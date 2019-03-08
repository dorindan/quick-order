INSERT INTO public.users (id, username, password, email) VALUES (1, 'admin', 'parola','admin@yahoo.com');

INSERT INTO public.user_attribute (id, user_id, language) VALUES (1, 1, 'EN');

INSERT INTO public.ingredient (id, name) VALUES (1, 'carne de porc');
INSERT INTO public.ingredient (id, name) VALUES (2, 'carne de vita');
INSERT INTO public.ingredient (id, name) VALUES (3, 'rosii');
INSERT INTO public.ingredient (id, name) VALUES (4, 'carnati');
INSERT INTO public.ingredient (id, name) VALUES (5, 'cremwusti');
INSERT INTO public.ingredient (id, name) VALUES (6, 'ciolan');
INSERT INTO public.ingredient (id, name) VALUES (7, 'fasole');
INSERT INTO public.ingredient (id, name) VALUES (8, 'cartofi');
INSERT INTO public.ingredient (id, name) VALUES (9, 'peste');
INSERT INTO public.ingredient (id, name) VALUES (10, 'paine');

INSERT INTO public.menu_item_type (id, type) VALUES (1, 'pizza');
INSERT INTO public.menu_item_type (id, type) VALUES (2, 'drinks');
INSERT INTO public.menu_item_type (id, type) VALUES (3, 'main course');


INSERT INTO public.menu_item (id, menu_item_type_id, name, description, preparation_duration_in_minutes, price) VALUES (3, 2, 'Santal', 'ii bun', 2, 8);
INSERT INTO public.menu_item (id, menu_item_type_id, name, description, preparation_duration_in_minutes, price) VALUES (1, 3, 'Ciolan cu fasole', 'ii bun', 25, 28);
INSERT INTO public.menu_item (id, menu_item_type_id, name, description, preparation_duration_in_minutes, price) VALUES (2, 1, 'Pizza Cannibale', 'ii bun', 30, 22);
INSERT INTO public.menu_item (id, menu_item_type_id, name, description, preparation_duration_in_minutes, price) VALUES (4, 2, 'Fanta', 'nu ii sanatos', 2, 15);

INSERT INTO public.menu_item_ingredient (id, menu_item_id, ingredient_id) VALUES (1, 1, 6);
INSERT INTO public.menu_item_ingredient (id, menu_item_id, ingredient_id) VALUES (2, 1, 7);
INSERT INTO public.menu_item_ingredient (id, menu_item_id, ingredient_id) VALUES (3, 2, 4);