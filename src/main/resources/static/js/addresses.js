async function showAddresses() {
    await loadAddresses();
}

async function loadAddresses() {
    const search = document.getElementById("searchAddress")?.value || "";

    try {
        const res = await fetch(`${ADDRESSES_API}?search=${search}`);
        if (!res.ok) throw new Error('Ошибка загрузки адресов');
        const addresses = await res.json();

        let html = `
            <div class="users-container">
                <div class="toolbar">
                    <input id="searchAddress" placeholder="Поиск по городу или улице..." value="${escapeHtml(search)}">
                    <button class="search-btn" onclick="loadAddresses()">Найти</button>
                    <button class="add-btn" onclick="showAddressForm()">Добавить адрес</button>
                </div>

                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Город</th>
                            <th>Улица</th>
                            <th>Действия</th>
                        </tr>
                    </thead>
                    <tbody>
        `;

        if (addresses.length === 0) {
            html += `
                <tr class="empty-row">
                    <td colspan="4">Адреса не найдены</td>
                </tr>
            `;
        } else {
            addresses.forEach(a => {
                html += `
                    <tr>
                        <td>${a.id}</td>
                        <td>${escapeHtml(a.city)}</td>
                        <td>${escapeHtml(a.street)}</td>
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
        throw new Error('Ошибка');
    }
}

function showAddressForm(address = {}, errors = {}) {
    document.getElementById("app").innerHTML = `
        <div class="form-container">
            <h2>${address.id ? 'Редактирование адреса' : 'Добавление адреса'}</h2>

            <div class="form-group">
                <label class="required">Город</label>
                <input id="city" placeholder="Введите город" value="${escapeHtml(address.city || '')}">
                <div class="error-message">${escapeHtml(errors.city || '')}</div>
            </div>

            <div class="form-group">
                <label class="required">Улица</label>
                <input id="street" placeholder="Введите улицу" value="${escapeHtml(address.street || '')}">
                <div class="error-message">${escapeHtml(errors.street || '')}</div>
            </div>

            <div class="button-group">
                <button class="save-btn" onclick="saveAddress(${address.id || 'null'})">Сохранить</button>
                <button class="back-btn" onclick="showAddresses()">Назад</button>
            </div>
        </div>
    `;
}

async function saveAddress(id) {
    const address = {
        id: id,
        city: document.getElementById("city").value,
        street: document.getElementById("street").value
    };

    let method = "POST";
    let url = ADDRESSES_API;

    if (id) {
        method = "PUT";
        url = `${ADDRESSES_API}/${id}`;
    }

    try {
        const response = await fetch(url, {
            method: method,
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(address)
        });

        if (!response.ok) {
            const errors = await response.json();
            showAddressForm(address, errors);
            return;
        }
        showAddresses();
    } catch (error) {
        throw new Error('Ошибка сохранения');
    }
}

async function editAddress(id) {
    try {
        const res = await fetch(`${ADDRESSES_API}/${id}`);
        if (!res.ok) throw new Error('Адрес не найден');
        const address = await res.json();
        showAddressForm(address);
    } catch (error) {
        throw new Error('Ошибка поиска адреса');
    }
}

async function deleteAddress(id) {
    if (confirm('Вы уверены, что хотите удалить этот адрес?')) {
        try {
            const response = await fetch(`${ADDRESSES_API}/${id}`, { method: "DELETE" });
            if (!response.ok) throw new Error('Ошибка удаления');
            showAddresses();
        } catch (error) {
            throw new Error('Ошибка удаления');
        }
    }
}