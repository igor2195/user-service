async function showUsers() {
    await loadUsers();
}

async function loadUsers() {
    const search = document.getElementById("search")?.value || "";

    try {
        const res = await api(`${USERS_API}?search=${search}`);
        if (!res.ok) throw new Error('Ошибка загрузки пользователей');

        const users = await res.json();

        let html = `
            <div class="users-container">
                <h2>Пользователи</h2>

                <div class="toolbar">
                    <input id="search" placeholder="Поиск по имени или email" value="${escapeHtml(search)}">
                    <button onclick="loadUsers()">Найти</button>
                    <button onclick="showUserForm()" class="add-btn">Добавить</button>
                </div>

                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Имя</th>
                            <th>Фамилия</th>
                            <th>Отчество</th>
                            <th>Email</th>
                            <th>Телефон</th>
                            <th>Дата рождения</th>
                            <th>Адрес</th>
                            <th>Действия</th>
                        </tr>
                    </thead>
                    <tbody>
        `;

        if (users.length === 0) {
            html += `
                <tr class="empty-row">
                    <td colspan="9">Пользователи не найдены</td>
                </tr>
            `;
        } else {
            users.forEach(u => {

                let addressText = '-';

                if (u.address) {
                    const a = u.address;
                    addressText = `${a.region}, ${a.city}, ${a.street}, ${a.house}${
                        a.apartment ? ', кв.' + a.apartment : ''
                    }`;
                }

                html += `
                    <tr>
                        <td>${u.id}</td>
                        <td>${escapeHtml(u.firstName)}</td>
                        <td>${escapeHtml(u.lastName || '-')}</td>
                        <td>${escapeHtml(u.middleName || '-')}</td>
                        <td>${escapeHtml(u.email || '-')}</td>
                        <td>${escapeHtml(u.phone || '-')}</td>
                        <td>${u.birthDate ? new Date(u.birthDate).toLocaleDateString() : '-'}</td>
                        <td>${escapeHtml(addressText)}</td>
                        <td class="action-buttons">
                            <button class="edit-btn" onclick="editUser(${u.id})">Редактировать</button>
                            <button class="delete-btn" onclick="deleteUser(${u.id})">Удалить</button>
                        </td>
                    </tr>
                `;
            });
        }

        html += `
                    </tbody>
                </table>
            </div>
        `;

        document.getElementById("app").innerHTML = html;

    } catch (error) {
        showError(error.message);
    }
}

async function showUserForm(user = {}, errors = {}) {

    const hasId = user.id != null;

    let addresses = [];
    try {
        const res = await api(ADDRESSES_API);
        if (res.ok) addresses = await res.json();
    } catch (e) {
        console.error("Ошибка загрузки адресов", e);
    }

    let addressOptions = '<option value="">-- выберите адрес --</option>';

    addresses.forEach(a => {
        const selected = user.address?.id == a.id ? "selected" : "";

        addressOptions += `
            <option value="${a.id}" ${selected}>
                ${escapeHtml(a.region)}, ${escapeHtml(a.city)}, ${escapeHtml(a.street)}, ${escapeHtml(a.house)}
                ${a.apartment ? ', кв.' + a.apartment : ''}
            </option>
        `;
    });

    const birthDateValue = user.birthDate
        ? new Date(user.birthDate).toISOString().split('T')[0]
        : '';

    document.getElementById("app").innerHTML = `
        <div class="form-container">
            <h2>${hasId ? 'Редактирование пользователя' : 'Добавление пользователя'}</h2>

            <div class="form-group">
                <label>Имя *</label>
                <input id="firstName" value="${escapeHtml(user.firstName || '')}">
                <div id="firstName-error" class="error-message"></div>
            </div>

            <div class="form-group">
                <label>Фамилия</label>
                <input id="lastName" value="${escapeHtml(user.lastName || '')}">
                <div id="lastName-error" class="error-message"></div>
            </div>

            <div class="form-group">
                <label>Отчество</label>
                <input id="middleName" value="${escapeHtml(user.middleName || '')}">
                <div id="middleName-error" class="error-message"></div>
            </div>

            <div class="form-group">
                <label>Телефон</label>
                <input id="phone" value="${escapeHtml(user.phone || '')}">
                <div id="phone-error" class="error-message"></div>
            </div>

            <div class="form-group">
                <label>Email</label>
                <input id="email" value="${escapeHtml(user.email || '')}">
                <div id="email-error" class="error-message"></div>
            </div>

            <div class="form-group">
                <label>Дата рождения *</label>
                <input id="birthDate" type="date" value="${birthDateValue}">
                <div id="birthDate-error" class="error-message"></div>
            </div>

            <div class="form-group">
                <label>Адрес</label>
                <select id="addressId">
                    ${addressOptions}
                </select>
            </div>

            <div class="button-group">
                <button onclick="saveUser(${user.id ?? 'null'})">Сохранить</button>
                <button onclick="showUsers()">Отмена</button>
            </div>
        </div>
    `;

    if (Object.keys(errors).length > 0) {
        applyFieldErrors(errors);
    }
}

async function saveUser(id) {

    const addressId = document.getElementById("addressId").value;

    const user = {
        id: id,
        firstName: document.getElementById("firstName").value,
        lastName: document.getElementById("lastName").value,
        middleName: document.getElementById("middleName").value,
        phone: document.getElementById("phone").value,
        email: document.getElementById("email").value,
        birthDate: document.getElementById("birthDate").value,
        address: addressId !== "" && addressId !== null && addressId !== undefined
                ? { id: Number(addressId) }
                : null
    };

    let method = "POST";
    let url = USERS_API;

    if (id != null) {
        method = "PUT";
        url = `${USERS_API}/${id}`;
    }

    try {
        const response = await api(url, {
            method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(user)
        });

        if (!response.ok) {
            const errorData = await response.json().catch(() => ({}));

            if (errorData.errors) {
                showUserForm(user, errorData.errors);
                return;
            }

            throw new Error(errorData.message || "Ошибка сохранения");
        }

        showSuccess(id ? "Пользователь обновлён" : "Пользователь создан");
        showUsers();

    } catch (error) {
        showError(error.message);
    }
}

async function editUser(id) {
    try {
        const res = await api(`${USERS_API}/${id}`);
        if (!res.ok) throw new Error("Пользователь не найден");

        const user = await res.json();
        showUserForm(user);

    } catch (error) {
        showError(error.message);
    }
}

async function deleteUser(id) {

    if (!confirm("Удалить пользователя?")) return;

    try {
        const res = await api(`${USERS_API}/${id}`, {
            method: "DELETE"
        });

        if (!res.ok) {
            const err = await res.json().catch(() => ({}));
            throw new Error(err.message || "Ошибка удаления");
        }

        showSuccess("Пользователь удалён");
        showUsers();

    } catch (error) {
        showError(error.message);
    }
}