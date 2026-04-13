async function showAddresses() {
    await loadAddresses();
}

async function loadAddresses() {
    const search = document.getElementById("searchAddress")?.value || "";

    try {
        const res = await api(`${ADDRESSES_API}?search=${search}`);
        if (!res.ok) throw new Error('Ошибка загрузки адресов');
        const addresses = await res.json();

        let html = `
            <div class="addresses-container">
                <h2>Адреса</h2>

                <div class="toolbar">
                    <input id="searchAddress" placeholder="Поиск по городу или улице" value="${escapeHtml(search)}">
                    <button onclick="loadAddresses()">Найти</button>
                    <button onclick="showAddressForm()" class="add-btn">Добавить адрес</button>
                </div>

                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Область</th>
                            <th>Город</th>
                            <th>Улица</th>
                            <th>Дом</th>
                            <th>Квартира</th>
                            <th>Действия</th>
                        </tr>
                    </thead>
                    <tbody>
        `;

        if (addresses.length === 0) {
            html += `
                <tr class="empty-row">
                    <td colspan="7">Адреса не найдены</td>
                </tr>
            `;
        } else {
            addresses.forEach(a => {
                html += `
                    <tr>
                        <td>${a.id}</td>
                        <td>${escapeHtml(a.region)}</td>
                        <td>${escapeHtml(a.city)}</td>
                        <td>${escapeHtml(a.street)}</td>
                        <td>${escapeHtml(a.house)}</td>
                        <td>${escapeHtml(a.apartment || '-')}</td>
                        <td class="action-buttons">
                            <button class="edit-btn" onclick="editAddress(${a.id})">Редактировать</button>
                            <button class="delete-btn" onclick="deleteAddress(${a.id})">Удалить</button>
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

function showAddressForm(address = {}, errors = {}) {
    const hasId = address.id !== undefined && address.id !== null;

    document.getElementById("app").innerHTML = `
        <div class="form-container">
            <h2>${hasId ? 'Редактирование адреса' : 'Добавление адреса'}</h2>

            <div class="form-group">
                <label>Область *</label>
                <input id="region" placeholder="Введите область" value="${escapeHtml(address.region || '')}">
                <div id="region-error" class="error-message"></div>
            </div>

            <div class="form-group">
                <label>Город *</label>
                <input id="city" placeholder="Введите город" value="${escapeHtml(address.city || '')}">
                <div id="city-error" class="error-message"></div>
            </div>

            <div class="form-group">
                <label>Улица *</label>
                <input id="street" placeholder="Введите улицу" value="${escapeHtml(address.street || '')}">
                <div id="street-error" class="error-message"></div>
            </div>

            <div class="form-group">
                <label>Дом *</label>
                <input id="house" placeholder="Введите номер дома" value="${escapeHtml(address.house || '')}">
                <div id="house-error" class="error-message"></div>
            </div>

            <div class="form-group">
                <label>Квартира</label>
                <input id="apartment" placeholder="Введите номер квартиры" value="${escapeHtml(address.apartment || '')}">
                <div id="apartment-error" class="error-message"></div>
            </div>

            <div class="button-group">
                <button class="save-btn" onclick="saveAddress(${address.id != null ? address.id : 'null'})">Сохранить</button>
                <button class="back-btn" onclick="showAddresses()">Назад</button>
            </div>
        </div>
    `;

    if (Object.keys(errors).length > 0) {
        applyFieldErrors(errors);
    }
}

async function saveAddress(id) {
    const address = {
        id: id,
        region: document.getElementById("region").value,
        city: document.getElementById("city").value,
        street: document.getElementById("street").value,
        house: document.getElementById("house").value,
        apartment: document.getElementById("apartment").value
    };

    let method = "POST";
    let url = ADDRESSES_API;

    // ✅ фикс
    if (id !== null && id !== undefined) {
        method = "PUT";
        url = `${ADDRESSES_API}/${id}`;
    }

    try {
        const response = await api(url, {
            method: method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(address)
        });

        if (!response.ok) {
            let errors = {};

            try {
                const errorData = await response.json();
                if (errorData.errors) {
                    errors = errorData.errors;
                } else if (errorData.message) {
                    showError(errorData.message);
                    return;
                }
            } catch (e) {
                showError("Ошибка сервера");
                return;
            }

            showAddressForm(address, errors);
            return;
        }

        showSuccess(id ? 'Адрес обновлен' : 'Адрес создан');
        showAddresses();

    } catch (error) {
        showError(error.message);
    }
}

async function editAddress(id) {
    try {
        const res = await api(`${ADDRESSES_API}/${id}`);
        if (!res.ok) throw new Error('Адрес не найден');
        const address = await res.json();
        showAddressForm(address);
    } catch (error) {
        showError(error.message);
    }
}

async function deleteAddress(id) {
    if (confirm('Вы уверены, что хотите удалить этот адрес?')) {
        try {
            const response = await api(`${ADDRESSES_API}/${id}`, { method: "DELETE" });
            if (!response.ok) throw new Error('Ошибка удаления');
            showSuccess('Адрес удален');
            showAddresses();
        } catch (error) {
            showError(error.message);
        }
    }
}