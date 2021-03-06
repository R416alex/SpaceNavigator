function [R1, Vp1, V1, R2, Vp2, V2, tof, delta_v_total, Rspacecraft] = TestAlg(planet_id1, year1, month1, day1, hour1, min1, sec1, a_parking, planet_id2, year2, month2, day2, hour2, min2, sec2, r_capture)
%This algorithm utilizes the interplanetary mission design program

global mu
mu = 1.327124e11;
deg = pi/180;

%% CALCULATION OF MISSION PARAMETERS

%...Departure parameters
departure = [planet_id1, year1, month1, day1, hour1, min1, sec1];
%...Arrival parameters 
arrival = [planet_id2, year2, month2, day2, hour2, min2, sec2];

%Obtain orbital elements and planets' state vectors 
[oe1, r1, v1, ~] = planet_oe_and_sv(planet_id1, year1, month1,...
    day1, hour1, min1, sec1,1);
[oe2, r2, v2, ~] = planet_oe_and_sv(planet_id2, year2, month2,...
    day2, hour2, min2, sec2,1);

[oe1_prime, r1_prime, v1_prime, jd1_prime] = planet_oe_and_sv(planet_id1,...
    year2, month2, day2, hour2, min2, sec2,1);
[oe2_prime, r2_prime, v2_prime, jd2_prime] = planet_oe_and_sv(planet_id2,...
    year1, month1, day1, hour1, min1, sec1,1);

%...Interplanetary trajectory
[planet1, planet2, trajectory] = heliocentric_trajectory(departure, arrival);
%Planet1 state vector
R1 = planet1(1,1:3);
%Planet1 velocity vector
Vp1 = planet1(1,4:6);
%Planet1 julian day
jd1 = planet1(1,7);

%Planet2 state vector
R2 = planet2(1,1:3);
%Planet2 velocity vector
Vp2 = planet2(1,4:6);
%Planet2 julian day
jd2 = planet2(1,7);

%Space vehicle velocity at departure
V1 = trajectory(1,1:3);
%Space vehicle velocity at arrival
V2 = trajectory(1,4:6);
%Time of flight
tof = jd2 - jd1;

%Orbital elements of the space vehicle's trajectory based on [Rp1, V1]...
oe = oe_from_sv(R1, V1, mu);
% ...and [R2, V2]

if oe(2) >= 1
    error("e>=1");
end
oe3 = oe_from_sv(R2, V2, mu);
%Velocitis at infinity 
vinf1 = V1 - Vp1;
if planet_id1 < planet_id2
    vinf2 = V2 - Vp2;
else
    vinf2 = Vp2 - V2;
end

%Planet1 orbit period
T1 = 2*pi/sqrt(mu)*oe1(7)^(3/2)/3600/24;
%Planet2 orbit period
T2 = 2*pi/sqrt(mu)*oe2(7)^(3/2)/3600/24;
%Transfer orbit period
T3 = 2*pi/sqrt(mu)*oe(7)^(3/2)/3600/24;

%...Planetary departure parameters 
%Planet1 astronomical data
planet1_astronomical_data = astronomical_data(planet_id1);
%Radius of planet1
r_planet1 = planet1_astronomical_data(1);
%Gravitaional parameter of planet1
mu_planet1 = planet1_astronomical_data(3);
%Radius of the circular parking orbit
rp1 = r_planet1 + a_parking;
%Speed at the periapsis of the departure parabola
vp1 = sqrt(norm(vinf1)^2 + 2*mu_planet1/rp1);
%Speed of the circular parking orbit
vC1 = sqrt(mu_planet1/rp1);
%Delta_v required for the maneuver
delta_v_departure = vp1 - vC1;
%Eccentricity of the hyperbola at departure
e_dep = 1 + rp1*norm(vinf1)^2/mu_planet1;
%Period of the circular parking orbit
T_parking = 2*pi/sqrt(mu_planet1)*rp1^(3/2)/60;

%...Planetary arrival parameters
%Planet2 astronomical data
planet2_astronomical_data = astronomical_data(planet_id2);
%Radius of planet2
rp2 = planet2_astronomical_data(1);
%Gravitaional parameter of planet2
mu_planet2 = planet2_astronomical_data(3);
%Radius of the circular capture orbit
r_p_arrival = rp2 + r_capture;
%Speed at the periapsis of the departure parabola
vp2 = sqrt(norm(vinf2)^2+2*mu_planet2/r_p_arrival);
%Speed of the circular capture orbit
vC2 = sqrt(mu_planet2/r_p_arrival);
%Delta_v required for the maneuver
delta_v_arrival = vp2 - vC2;
%Eccentricity of the hyperbola at arrival
e_arrive = 1 + r_p_arrival*norm(vinf2)^2/mu_planet2;
%Period of the circular capture orbit
T_parking2 = 2*pi/sqrt(mu_planet2)*r_p_arrival^(3/2)/60;

%Total delta_v for the mission 
delta_v_total = delta_v_departure + delta_v_arrival;

    cosE1 = (oe(2)+cos(oe(6)))/(1+(oe(2)*cos(oe(6)))); 
 
    if 0 < oe(6) && oe(6) < pi
        E1 = acos(cosE1);
    else
        E1 = 2*pi-acos(cosE1);
    end
 
%    if oe(2) > 1
%       % M1 = i*(E1-(oe(2)*sin(E1)))
%       M1 = i*(oe(2)*sinh(E1))-E1;
%    else 
%    M1 = E1-(oe(2)*sin(E1));
%    end
 % E1 = acos((oe(2)+cos(oe(6)))/(1+(oe(2)*cos(oe(6)))));
N = sqrt(mu/(oe(7)^3));
M1 = E1-(oe(2)*sin(E1));
   
   t = M1/N;
   c = 1;
for p = 0:tof
     M2 = N*(t+(p*86400));
    E = kepler_equation(oe(2), M2);
    TA = 2*atan(sqrt((1 + oe(2))/(1 - oe(2)))*tan(E/2));
    [rc(c,:), vc(c,:)] = sv_from_oe([oe(1), oe(2), oe(3), oe(4), oe(5), TA],mu);
   c = c + 1;
   M2 = N*(t+((p*86400)+(43200)));
    E = kepler_equation(oe(2), M2);
    TA = 2*atan(sqrt((1 + oe(2))/(1 - oe(2)))*tan(E/2));
    [rc(c,:), vc(c,:)] = sv_from_oe([oe(1), oe(2), oe(3), oe(4), oe(5), TA],mu);
    c = c + 1;
end

Rspacecraft = [rc];

% %Error message in case of absurd choices for departure and arrival times
% if delta_v_departure > 60 || delta_v_arrival > 60 || tof < 0
%     clc;
%     try_again = ['\n\nPlease be careful with the dates of departure'...
%             ' and arrival.Depending on \nthe planets of choice the'...
%             'time of flight should range from several \nmonths up to'...
%             'several years and even tens of years.\n *Try again*\n'];
%     fprintf(try_again);
%     pause(5);
% else 
%     break
% end
end