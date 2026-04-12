async function showUsers() {
    await loadUsers();
}

async function loadUsers() {
    const search = document.getElementById("search")?.value || "";
    try {
        const res = await fetch(`${USERS_API}?search=${search}`);
        if (!res.ok) throw new Error('Ошибка загрузки пользователей');
        const users = await res.json();

        let html = `
            <div class="users-container">
                <div class="toolbar">
                    <input id="search" placeholder="Поиск по имени" value="${escapeHtml(search)}">
                    <button class="search-btn" onclick="loadUsers()">Найти</button>
                    <button class="add-btn" onclick="showUserForm()">Добавить пользователя</button>
                </div>

                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>ФИО</th>
                            <th>Email</th>
                            <th>Телефон</th>
                            <th>Дата рождения</th>
                            <th>Действия</th>
                        </tr>
                    </thead>
                    <tbody>
        `;

        if (users.length === 0) {
            html += `
                <tr class="empty-row">
                    <td colspan="6">Пользователи не найдены</td>
                </tr>
            `;
        } else {
            users.forEach(u => {
                const fullName = [u.lastName, u.firstName, u.middleName]
                    .filter(n => n && n.trim())
                    .join(' ') || '-';

                html += `
                    <tr>
                        <td>${u.id}</td>
                        <td>${escapeHtml(fullName)}</td>
                        <td>${escapeHtml(u.email || '-')}</td>
                        <td>${escapeHtml(u.phone || '-')}</td>
                        <td>${u.birthDate || '-'}</td>
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
        throw new Error('Ошибка');
    }
}

function showUserForm(user = {}, errors = {}) {
    document.getElementById("app").innerHTML = `
        <div class="form-container">
            <h2>${user.id ? 'Редактирование пользователя' : 'Добавление пользователя'}</h2>

            <div class="form-group">
                <label class="required">Имя</label>
                <input id="firstName" placeholder="Введите имя" value="${escapeHtml(user.firstName || '')}">
                <div class="error-message">${escapeHtml(errors.firstName || '')}</div>
            </div>

            <div class="form-group">
                <label>Фамилия</label>
                <input id="lastName" placeholder="Введите фамилию" value="${escapeHtml(user.lastName || '')}">
                <div class="error-message">${escapeHtml(errors.lastName || '')}</div>
            </div>

            <div class="form-group">
                <label>Отчество</label>
                <input id="middleName" placeholder="Введите отчество" value="${escapeHtml(user.middleName || '')}">
                <div class="error-message">${escapeHtml(errors.middleName || '')}</div>
            </div>

            <div class="form-group">
                <label>Телефон</label>
                <input id="phone" placeholder="+7 (XXX) XXX-XX-XX" value="${escapeHtml(user.phone || '')}">
                <div class="error-message">${escapeHtml(errors.phone || '')}</div>
            </div>

            <div class="form-group">
                <label>Email</label>
                <input id="email" placeholder="user@example.com" value="${escapeHtml(user.email || '')}">
                <div class="error-message">${escapeHtml(errors.email || '')}</div>
            </div>

            <div class="form-group">
                <label>Дата рождения</label>
                <input id="birthDate" type="date" value="${user.birthDate || ''}">
                <div class="error-message">${escapeHtml(errors.birthDate || '')}</div>
            </div>

            <div class="button-group">
                <button class="save-btn" onclick="saveUser(${user.id || 'null'})">Сохранить</button>
                <button class="back-btn" onclick="showUsers()">Назад</button>
            </div>
        </div>
    `;
}

async function saveUser(id) {
    const user = {
        id: id,
        firstName: document.getElementById("firstName").value,
        lastName: document.getElementById("lastName").value,
        middleName: document.getElementById("middleName").value,
        phone: document.getElementById("phone").value,
        email: document.getElementById("email").value,
        birthDate: document.getElementById("birthDate").value
    };

    let method = "POST";
    let url = USERS_API;

    if (id) {
        method = "PUT";
        url = `${USERS_API}/${id}`;
    }

    try {
        const response = await fetch(url, {
            method: method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(user)
        });

        if (!response.ok) {
            const errors = await response.json();
            showUserForm(user, errors);
            return;
        }
        showUsers();
    } catch (error) {
        throw new Error('Ошибка');
    }
}

async function editUser(id) {
    try {
        const res = await fetch(`${USERS_API}/${id}`);
        if (!res.ok) throw new Error('Пользователь не найден');
        const user = await res.json();
        showUserForm(user);
    } catch (error) {
        throw new Error('Ошибка');
    }
}

async function deleteUser(id) {
    if (confirm('Вы уверены, что хотите удалить этого пользователя?')) {
        try {
            const response = await fetch(`${USERS_API}/${id}`, { method: "DELETE" });
            if (!response.ok) throw new Error('Ошибка удаления');
            showUsers();
        } catch (error) {
            throw new Error('Ошибка');
        }
    }
}