insert into Band (name, area) values
('Fierce Fangs', 'Eastern Valley'),
('Wise Rocks', 'Mountain Range'),
('Swift Feet', 'Western Plains'),
('Fiery Hearts', 'Northern Caves'),
('Silent Hunters', 'Southern Forest'),
('Steel Teeth', 'Riverside Zone'),
('Ancient Stones', 'Central Plateau');

insert into Invention (name, weight) values
('Club', 3.2),
('Saw', 1.5),
('Horn Dagger', 0.6),
('Bone Scraper', 0.4),
('Stone Axe', 2.5),
('Spear', 1.8),
('Bow', 0.9),
('Stone Knife', 0.4);

insert into Pithecanthropus (name, band_id, weight, height) values
('Grog', 1, 85.5, 175),
('Ug', 1, 92.0, 182),
('Tor', 1, 78.3, 168),
('Rok', 2, 88.7, 179),
('Zar', 2, 81.2, 172),
('Grom', 2, 95.1, 185),
('Arrow', 3, 72.4, 165),
('Wind', 3, 76.8, 170),
('Deer', 3, 80.5, 174),
('Fire', 4, 89.3, 177),
('Spark', 4, 68.9, 162),
('Flame', 4, 91.7, 181),
('Shadow', 5, 74.2, 169),
('Night', 5, 77.6, 171),
('Moon', 5, 70.1, 166),
('River', 6, 83.4, 176),
('Water', 6, 79.8, 173),
('Rain', 6, 86.2, 178),
('Mountain', 7, 94.5, 184),
('Stone', 7, 90.3, 180);

insert into Action (name, start, finish) values
('Mammoth Hunt', '4712-03-15 06:00:00', '4712-03-15 14:00:00'),
('Weapon Crafting', '4712-03-15 09:00:00', '4712-03-15 12:00:00'),
('Berry Gathering', '4712-03-15 08:00:00', '4712-03-15 11:00:00'),
('Fire Making', '4712-03-15 07:00:00', '4712-03-15 08:00:00'),
('Shelter Building', '4711-03-16 06:00:00', '4711-03-16 18:00:00'),
('Fishing', '4711-03-16 05:00:00', '4711-03-16 09:00:00'),
('Youth Training', '4711-03-16 10:00:00', '4711-03-16 14:00:00'),
('Territory Defense', '4710-03-17 00:00:00', '4710-03-17 06:00:00'),
('Food Preparation', '4710-03-17 12:00:00', '4710-03-17 14:00:00'),
('Clothing Making', '4710-03-17 09:00:00', '4710-03-17 15:00:00'),
('Ritual Dance', '4710-03-17 20:00:00', '4710-03-17 23:00:00'),
('Healing Wounded', '4709-03-18 08:00:00', '4709-03-18 12:00:00');

insert into Pithec_action (pithec_id, action_id) values
(1, 1), (2, 1), (3, 1),
(4, 2), (5, 2),
(6, 3), (7, 3), (8, 3),
(9, 4), (10, 4),
(11, 5), (12, 5), (13, 5), (14, 5),
(15, 6), (16, 6),
(17, 7), (18, 7),
(19, 8), (20, 8), (1, 8),
(2, 9), (3, 9), (4, 9),
(5, 10), (6, 10),
(7, 11), (8, 11), (9, 11),
(10, 12), (11, 12), (12, 12);

insert into Invention_in_action (action_id, invention_id) values
(1, 5), (1, 6), (1, 1),
(2, 5), (2, 8), (2, 4),
(3, 6), (3, 8),
(4, 1), (4, 7),
(5, 5), (5, 4), (5, 7),
(6, 3), (6, 8),
(7, 6), (7, 1),
(8, 6), (8, 7),
(9, 8), (9, 6),
(10, 3), (10, 7),
(11, 1), (11, 7),
(12, 8), (12, 3);

insert into Invention_type (invention_id, applicability, material, suggested_sex_of_user) values
(1, 'Close combat, blunt weapon', 'Wood', 'male'),
(2, 'Cutting wood, processing materials', 'Stone, wood', 'male'),
(3, 'Close combat, piercing weapon', 'Horn, bone', 'male'),
(4, 'Scraping hides, cleaning bones', 'Bone', 'female'),
(5, 'Chopping trees, butchering', 'Stone, wood', 'male'),
(6, 'Hunting large game', 'Wood, stone', 'male'),
(7, 'Hunting small game at distance', 'Wood, fibers', 'male'),
(8, 'Butchering meat, cutting materials', 'Stone', 'all');

insert into Symboled_band_invention (type, band_id, invention_id) values
('Totem of Strength', 1, 5),      -- Fierce Fangs: Stone Axe
('Totem of Strength', 7, 1),      -- Ancient Stones: Club
('Totem of Hunt', 1, 6),          -- Fierce Fangs: Spear
('Totem of Hunt', 4, 6),          -- Fiery Hearts: Spear
('Totem of Wisdom', 2, 4),        -- Wise Rocks: Bone Scraper
('Totem of Wisdom', 5, 8),        -- Silent Hunters: Stone Knife
('Totem of Speed', 3, 7),         -- Swift Feet: Bow
('Totem of Speed', 6, 3),         -- Steel Teeth: Horn Dagger
('Totem of Fire', 4, 2),          -- Fiery Hearts: Saw
('Totem of Fire', 1, 1),          -- Fierce Fangs: Club
('Totem of Protection', 2, 1),    -- Wise Rocks: Club
('Totem of Protection', 7, 7);    -- Ancient Stones: Bow