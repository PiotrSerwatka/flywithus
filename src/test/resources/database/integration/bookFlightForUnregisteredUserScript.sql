insert into airport values
  (-1000, 'Wroclaw'),
  (-1001, 'Poznan'),
  (-1002, 'Krakow');

insert into flight (id, from_airport_id, to_airport_id, flight_date, number_of_seats, seat_price)  values
  (-2000, -1000, -1001, '2018-01-01 00:00:00.000', 10, 2.00),
  (-2001, -1000, -1001, '2018-01-04 13:00:00.000', 10, 2.00),
  (-2004, -1000, -1001, '2018-01-12 13:00:00.000', 10, 2.00),
  (-2005, -1002, -1002, '2018-01-10 23:59:59.999', 10, 2.00);
