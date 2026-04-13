INSERT INTO address (region, city, street, house, apartment)
VALUES
    ('Московская область', 'Москва', 'Тверская улица', '15', '78'),
    ('Ленинградская область', 'Санкт-Петербург', 'Невский проспект', '28', '12'),
    ('Новосибирская область', 'Новосибирск', 'Красный проспект', '45', NULL);


INSERT INTO users (first_name, last_name, middle_name, phone, email, birth_date, address_id)
VALUES
    ('Иван', 'Иванов', 'Иванович', '79161234567', 'ivan@example.com', '1990-05-15', 0),
    ('Петр', 'Петров', NULL, '79261234567', 'petr@example.com', '1985-08-20', 1),
    ('Мария', 'Сидорова', 'Алексеевна', '79361234567', 'maria@example.com', '1995-03-10', 2);