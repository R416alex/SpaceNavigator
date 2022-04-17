function planet_astronomical_data = astronomical_data(planet_id)
%   This fucntion extracts a planet's astronomical data.
%
%   INPUT:  
%       planet_id  =  planet identifier - 1 to 9, from Mercury to Pluto
%   OUTPUT: 
%       planet_astronomical_data 
%
%   VARIABLES DESCTRIPTION: 
%       astronomicalData - 9x7 array containing the atronomical 
%           data for the nine plnets. The columns of each row are:
%           R    - radius of the planet (km)
%           M    - mass of the planet (*10^24kg)
%           mu   - gravitational parameter (km^3/s^2)
%           SRP  - sidereal rotation period (hours)
%           IE   - inclination of equator to orbit plane (deg)
%           IOEP - inclination of orbit to ecliptic plane (deg)
%           OSP  - orbit sidereal period (days)
%       planet_astronomical_data - astronomical data correspomding 
%                                  to planet_id

%% Astronomical data array 
astronomicalData=...
[2440  0.3302 22030     1403.8  0.01  7.00  87.97
 6052  4.869  324900    5816.1  177.4 3.39  224.7
 6378  5.974  398600    23.9345 23.45 0     365.256
 3380  0.6419 42828     589.267 25.19 1.850 687.05
 71490 1899   126686000 9.925   3.13  1.304 4331.9
 60270 568.5  37931000  10.66   26.73 2.485 10760.44
 25560 86.83  5794000   17.24   97.77 0.772 30685.15
 24760 102.3  6835100   16.11   28.32 1.769 60194.19
 1195  0.0125 830       152.87  122.5 17.16 90473.91];
%% Astronomical data for the chosen planet
planet_astronomical_data = astronomicalData(planet_id,:);
end 