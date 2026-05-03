DROP TABLE IF EXISTS races;
DROP TABLE IF EXISTS drivers;

CREATE TABLE drivers (
                         driver_id INTEGER PRIMARY KEY,
                         driver_name TEXT NOT NULL,
                         car_number INTEGER NOT NULL,
                         nationality TEXT NOT NULL,
                         team TEXT NOT NULL,
                         races_entered INTEGER NOT NULL,
                         podiums INTEGER NOT NULL,
                         race_wins INTEGER NOT NULL,
                         total_points INTEGER NOT NULL,
                         active_status INTEGER NOT NULL CHECK (active_status IN (0,1))
);

CREATE TABLE races (
                       race_id INTEGER PRIMARY KEY,
                       driver_id INTEGER NOT NULL,
                       race_name TEXT NOT NULL,
                       location TEXT NOT NULL,
                       country TEXT NOT NULL,
                       race_date TEXT NOT NULL,
                       total_laps INTEGER NOT NULL,
                       position INTEGER NOT NULL,
                       result INTEGER NOT NULL,
                       FOREIGN KEY (driver_id) REFERENCES drivers(driver_id)
);

INSERT INTO drivers VALUES
    (1, 'Lando Norris', 1, 'UK', 'Mclaren', 24, 18, 8, 423, 1),
        (3, 'Max Verstappen', 3, 'NL', 'Red Bull', 24, 15, 8, 421, 1),
        (5, 'Gabriel Bortoleto', 5, 'BR', 'Sauber', 23, 0, 0, 19, 1),
        (6, 'Isack Hadjar', 16, 'FR', 'Red Bull', 23, 1, 0, 51, 1),
        (7, 'Jack Doohan', 7, 'AS', 'Alpine', 5, 0, 0, 0, 0),
        (10, 'Pierre Gasly', 10, 'FR', 'Alpine', 24, 0, 0, 22, 1),
        (12, 'Kimi Antonelli', 12, 'IT', 'Mercedes', 24, 3, 0, 150, 1),
        (14, 'Fernando Alonso', 14, 'SP', 'Aston Martin', 22, 0, 0, 56, 1),
        (16, 'Charles Leclerc', 16, 'MN', 'Ferrari', 24, 7, 0, 242, 1),
        (18, 'Lance Stroll', 18, 'CN', 'Aston Martin', 24, 0, 0, 33, 1),
        (22, 'Yuki Tsunoda', 22, 'JP', 'Racing Bulls', 24, 0, 0, 33, 1),
        (23, 'Alex Albon', 23, 'TH', 'Williams', 24, 0, 0, 73, 1),
        (27, 'Nico Hulkenberg', 27, 'GR', 'Haas', 24, 1, 0, 51, 1),
        (30, 'Liam Lawson', 30, 'NZ', 'Racing Bulls', 23, 0, 0, 38, 1),
        (31, 'Esteban Ocon', 31, 'FR', 'Haas', 24, 0, 0, 38, 1),
        (43, 'Franco Colapinto', 43, 'AR', 'Alpine', 18, 0, 0, 0, 1),
        (44, 'Lewis Hamilton', 44, 'UK', 'Ferrari', 24, 0, 0, 156, 1),
        (55, 'Carlos Sainz', 55, 'SP', 'Williams', 24, 2, 0, 64, 1),
        (63, 'George Russell', 63, 'UK', 'Mercedes', 24, 9, 2, 319, 1),
        (81, 'Oscar Piastri', 81, 'AS', 'Mclaren', 24, 16, 7, 410, 1),
        (87, 'Ollie Bearman', 87, 'UK', 'Haas', 24, 0, 0, 41, 1);

    INSERT INTO races VALUES
        (1, 1, 'LOUIS VUITTON AUSTRALIAN GRAND PRIX 2025', 'Albert Park Grand Prix Circuit, Melbourne', 'Australia', '16 MAR 2025', 58, 1, 25),
        (2, 81, 'HEINEKEN CHINESE GRAND PRIX 2025', 'Shanghai International Circuit, Shanghai', 'China', '23 MAR 2025', 56, 1, 25),
        (3, 3, 'LENOVO JAPANESE GRAND PRIX 2025', 'Suzuka Circuit, Suzuka', 'Japan', '06 Apr 2025', 25, 1, 25),
        (4, 81, 'GULF AIR BAHRAIN GRAND PRIX 2025', 'Bahrain International Circuit, Sakhir', 'Bahrain', '13 Apr 2025', 57, 1, 25),
        (5, 81, 'STC SAUDI ARABIAN GRAND PRIX 2025', 'Jeddah Corniche Circuit, Jeddah', 'Saudi Arabia', '20 Apr 2025', 50, 1, 25),
        (6, 81, 'CRYPTO.COM MIAMI GRAND PRIX 2025', 'Miami International Autodrome, Miami Gardens', 'United States', '04 May 2025', 57, 1, 25),
        (7, 3, 'AWS GRAN PREMIO DEL MADE IN ITALY E DELL''EMILIA-ROMAGNA 2025', 'Autodromo Internazionale Enzo e Dino Ferrari, Imola', 'Italy', '18 May 2025', 63, 1, 25),
        (8, 1, 'TAG HEUER GRAND PRIX DE MONACO 2025', 'Circuit de Monaco, Monaco', 'Monaco', '25 May 2025', 78, 1, 25),
        (9, 81, 'ARAMCO GRAN PREMIO DE ESPAÑA 2025', 'Circuit de Barcelona-Catalunya, Barcelona', 'Spain', '01 Jun 2025', 66, 1, 25),
        (10, 63, 'PIRELLI GRAND PRIX DU CANADA 2025', 'Circuit Gilles-Villeneuve, Montréal', 'Canada', '15 Jun 2025', 70, 1, 25),
        (11, 1, 'MSC CRUISES AUSTRIAN GRAND PRIX 2025', 'Red Bull Ring, Spielberg', 'Austria', '29 Jun 2025', 70, 1, 25),
        (12, 1, 'QATAR AIRWAYS BRITISH GRAND PRIX 2025', 'Silverstone Circuit, Silverstone', 'Great Britain', '06 Jul 2025', 52, 1, 25),
        (13, 81, 'MOËT & CHANDON BELGIAN GRAND PRIX 2025', 'Circuit de Spa-Francorchamps, Spa-Francorchamps', 'Belgium', '27 Jul 2025', 44, 1, 25),
        (14, 1, 'LENOVO HUNGARIAN GRAND PRIX 2025', 'Hungaroring, Budapest', 'Hungary', '03 Aug 2025', 70, 1, 25),
        (15, 81, 'HEINEKEN DUTCH GRAND PRIX 2025', 'Circuit Zandvoort, Zandvoort', 'Netherlands', '31 Aug 2025', 72, 1, 25),
        (16, 3, 'PIRELLI GRAN PREMIO D’ITALIA 2025', 'Autodromo Nazionale Monza, Monza', 'Italy', '07 Sep 2025', 53, 1, 25),
        (17, 3, 'QATAR AIRWAYS AZERBAIJAN GRAND PRIX 2025', 'Baku City Circuit, Baku', 'Azerbaijan', '21 Sep 2025', 51, 1, 25),
        (18, 63, 'SINGAPORE AIRLINES SINGAPORE GRAND PRIX 2025', 'Marina Bay Street Circuit, Marina Bay', 'Singapore', '05 Oct 2025', 62, 1, 25),
        (19, 3, 'MSC CRUISES UNITED STATES GRAND PRIX 2025 ', 'Circuit of The Americas, Austin', 'United States', '19 Oct 2025', 56, 1, 25),
        (20, 1, 'GRAN PREMIO DE LA CIUDAD DE MÉXICO 2025', 'Autódromo Hermanos Rodríguez, Mexico City', 'Mexico', '26 Oct 2025', 71, 1, 25),
        (21, 1, 'MSC CRUISES GRANDE PRÊMIO DE SÃO PAULO 2025', 'Autódromo José Carlos Pace, São Paulo', 'Brazil', '09 Nov 2025', 71, 1, 25),
        (22, 3, 'HEINEKEN LAS VEGAS GRAND PRIX 2025', 'Las Vegas Strip Circuit, Las Vegas', 'United States', '22 Nov 2025', 50, 1, 25),
        (23, 3, 'QATAR AIRWAYS QATAR GRAND PRIX 2025', 'Lusail International Circuit, Lusail', 'Qatar', '30 Nov 2025', 57, 1, 25),
        (24, 3, 'ETIHAD AIRWAYS ABU DHABI GRAND PRIX 2025', 'as Marina Circuit, Yas Island', 'Abu Dhabi', '07 Dec 2025', 58, 1, 25);