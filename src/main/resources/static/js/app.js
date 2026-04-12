// Общие константы
const USERS_API = "/user-service/api/v1/users";
const ADDRESSES_API = "/user-service/api/v1/addresses";

// Вспомогательная функция для защиты от XSS
function escapeHtml(str) {
    if (!str) return '';
    return String(str)
        .replace(/&/g, '&amp;')
        .replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/"/g, '&quot;')
        .replace(/'/g, '&#39;');
}

// Показ сообщений об ошибках
function showError(message) {
    alert('Ошибка: ' + message);
}
// Инициализация при загрузке
window.onload = function() {
    // По умолчанию показываем пользователей
    showUsers();
};