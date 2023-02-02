INSERT INTO locations("city", "geolocation")
    VALUES
        ('London',   ST_MAKEPOINT(51.509865, -0.118092)),
        ('New York', ST_MAKEPOINT(40.730610, -73.935242));
