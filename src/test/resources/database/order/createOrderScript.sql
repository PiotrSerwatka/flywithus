insert into airport values
  (-1000, 'Wroclaw'),
  (-1001, 'Poznan');

insert into flight (id, from_airport_id, to_airport_id, flight_date, number_of_seats)  values
  (-2000, -1000, -1001, '2018-01-01 10:00:00.000', 10);
