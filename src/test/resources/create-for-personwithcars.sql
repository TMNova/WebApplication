delete from person;
delete from car;

insert into person(id, name, birthday) values
                                           (1, 'Olya', '2000-12-12'),
                                           (2, 'Egor', '2020-12-12'),
                                           (3, 'Andrey', '2000-10-10'),
                                            (4, 'validperson', '2000-10-10');

insert into car(id, vendor, model, horsepower, owner_id_id) values
                                                                (1, 'BMW', '525d', 190, 1),
                                                                (2, 'KIA', 'Sportage', 184, 1),
                                                                (3, 'BMW', '525d', 190, 3),
                                                                (4, 'Volkswagen', 'Tiguan', 220, 3),
                                                                (5, 'Volkswagen', 'Tiguan', 150, 1),
                                                                (6, 'VAZ', '2105', 70, 4);