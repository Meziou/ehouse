INSERT INTO house (name) VALUES ('eHouse');

INSERT INTO room (name, created_at, id_house) VALUES 
('Kitchen', '2023-05-22', 1),
('Living room', '2023-06-13', 1),
('Bedroom', '2023-04-21', 1),
('Bathroom', '2023-007-02', 1);

INSERT INTO device (name, type, status, created_at, id_room) VALUES 
('Kitchen light', 'light', 'on', '2023-08-01', 1),
('Gas cooker', 'home appliance', 'off', '2023-08-01', 1),
('Dish washer', 'home appliance', 'off', '2023-08-01', 1),
('Fridge', 'home appliance', 'on', '2023-08-01', 1),
('Microwave', 'home appliance', 'on', '2023-08-01', 1),
('Living room light', 'light', 'on', '2023-08-01', 2),
('Television', 'multimedia', 'off', '2023-08-01', 2),
('Internet box', 'multimedia', 'off', '2023-08-01', 2),
('Playstation', 'multimedia', 'on', '2023-08-01', 2),
('Air Conditioner', 'home appliance', 'on', '2023-08-01', 2),
('Bedroom light', 'light', 'on', '2023-08-01', 3),
('Macbook', 'multimedia', 'off', '2023-08-01', 3),
('Switch 1', 'switch', 'off', '2023-08-01', 3),
('Computer', 'multimedia', 'on', '2023-08-01', 3),
('Heating', 'home appliance', 'of', '2023-08-01', 3),
('bathroom light 1', 'light', 'on', '2023-08-01', 4),
('bathroom light 2', 'light', 'on', '2023-08-01', 4),
('Hair dryer', 'multimedia', 'off', '2023-08-01', 4),
('Dish washer', 'home appliance', 'off', '2023-08-01', 4),
('Heating', 'home appliance', 'of', '2023-08-01', 4);
