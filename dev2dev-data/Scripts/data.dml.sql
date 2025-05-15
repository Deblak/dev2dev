DELETE FROM t_accounts;
DELETE FROM t_roles;
DELETE FROM t_notification_types;
DELETE FROM t_articles;
DELETE FROM t_articles_accounts;

INSERT INTO t_roles (name, role_default) VALUES 
('MEMBER', true),
('INTEGRATOR', false);

INSERT INTO t_articles (id, link, title, description, image, published_date)
VALUES 
(1, 'https://www.leparisien.fr/meteo/le-soleil-a-plus-brille-au-touquet-et-a-calais-qua-marseille-ou-nice-au-mois-davril-12-05-2025-JPL4SLEIIBA4BP72AIEN42DGTE.php', 'Le soleil a plus brillé au Touquet et à Calais qu’à Marseille ou Nice au mois d’avril', 'Le nord de la France connaît un excédent prononcé d’ensoleillement depuis le mois de février.'),
(2, 'https://www.leparisien.fr/culture-loisirs/tv/un-moment-hors-du-temps-brigitte-bardot-sur-bfmtv-les-coulisses-du-retour-a-lecran-de-la-star-12-05-2025-BGUFWDXMH5DLFPBXT22ET3VNWQ.php', '« Un moment hors du temps » : Brigitte Bardot sur BFMTV, les coulisses du retour à l’écran de la star', 'Steven Bellery, le chef du service culture de BFMTV qui a interviewé la star de 90 ans, nous raconte les coulisses de cette rencontre.'),
(3, 'https://www.airbnb.fr/rooms/848334798313556884', 'Hébergement · Bagnolet · ★4,83 · 3 chambres · 3 lits · 2 salles de bain', 'Jolie maison moderne avec rooftop'),
(4, 'https://www.airbnb.fr/rooms/1233083757282576289', 'Villa · Bagneux · ★4,90 · 5 chambres · 6 lits · 5 salles de bain', 'Maison de Verdure proche Paris'),
(5, 'https://www.airbnb.fr/rooms/1381286208276814905', 'Hébergement · Thiais · ★Nouveau · 4 chambres · 4 lits · 4 salles de bain', 'Sublime Villa piscine hammam cinema salle de sport'),
(6, 'https://www.airbnb.fr/rooms/1257348034596756903', 'Hébergement · Arcueil · ★4,96 · 2 chambres · 1 lit · 1 salle de bain partagée', '1 chambre + 1 petite chambre en supplément de prix'),
(7, 'https://www.airbnb.fr/rooms/1397810678476103080', 'Hébergement · Vanves · ★Nouveau · 2 chambres · 2 lits · 1 salle de bain privée', 'Chambre privée dans maison à Vanves, France'),
(8, 'https://www.airbnb.fr/rooms/1302093985052723053', 'Hébergement · Le Plessis-Robinson · ★4,73 · 2 chambres · 3 lits · 1 salle de bain et 1 toilette', 'Maison calme proche Paris');


INSERT INTO t_accounts (username, password, email_validate, id_role)
VALUES (
  'riri@mail.com', '$2a$12$S6f2tsM9xQv51P1zwlvSDuVLuGgHj4/T4ez6RTdTbgzpB3FnQAek.', '1', 
  (SELECT id FROM t_roles WHERE name = 'MEMBER'));

INSERT INTO t_accounts (username, password, email_validate, id_role)
VALUES (
  'fifi@mail.com', '$2a$12$S6f2tsM9xQv51P1zwlvSDuVLuGgHj4/T4ez6RTdTbgzpB3FnQAek.', '1', 
  (SELECT id FROM t_roles WHERE name = 'MEMBER'));

INSERT INTO t_accounts (username, password, email_validate, id_role)
VALUES (
  'loulou@mail.com', '$2a$12$S6f2tsM9xQv51P1zwlvSDuVLuGgHj4/T4ez6RTdTbgzpB3FnQAek.', '1', 
  (SELECT id FROM t_roles WHERE name = 'INTEGRATOR'));


INSERT INTO t_notification_types(type_name) VALUES
('ARTICLE'),
('RSS');