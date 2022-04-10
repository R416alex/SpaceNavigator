function A = planetPOS(planet_id, startDate, endDate)
%This function retrieves all position vectors between the start and end
%date
for i = 1:2
    if i == 1
        date = startDate;
    else
        date = endDate;
    end
    year = date(1);
    month = date(2);
    day = date(3);
    hour = date(4);
    minute = date(5);
    second = date(6);

j0 = Julian0(year, month, day);
ut = (hour + minute/60 + second/3600)/24;
%Calculate the julian day of the date and time
jd(i) = j0 + ut;
end

count = 1;
    for j = jd(1):jd(2)
        
        [~, r(count,:), ~, ~] = planet_oe_and_svJD(planet_id,j,2);
        count = count + 1;
    end

A = r;

end

function [oe, r, v, jd] = planet_oe_and_svJD(planet_id, jd, option)

%% Constants
if option == 1
global mu
else
    mu = 1.327124e11;
end
deg = pi/180;

% %% Calculate julian day at 0 hr and universal time
% j0 = Julian0(year, month, day);
% ut = (hour + minute/60 + second/3600)/24;
% %Calculate the julian day of the date and time
% jd = j0 + ut;

%% Obtain the data for the selected planet
[J2000_oe, rates] = planetary_ephemeris(planet_id);
t0 = (jd - 2451545)/36525;
el = J2000_oe + rates*t0;
a = el(1);
e = el(2);
h = sqrt(mu*a*(1 - e^2));

%% Reduce the angular elements within the range 0 - 360 degrees:
inclination = el(3);
RA = wrapTo360(el(4));
w_hat = wrapTo360(el(5));
L = wrapTo360(el(6));
w = wrapTo360(w_hat - RA);
M = wrapTo360((L - w_hat));

%% Calculate eccentric anomaly and true anomaly
E = kepler_equation(e, M*deg);
TA = wrapTo360(2*atan(sqrt((1 + e)/(1 - e))*tan(E/2))/deg);

%% Orbital elements vector  
oe = [h e RA inclination w TA a w_hat L M E/deg];
%% Calculate the state vector
[r, v] = sv_from_oe([h, e, RA*deg, inclination*deg, w*deg, TA*deg],mu);
return
end