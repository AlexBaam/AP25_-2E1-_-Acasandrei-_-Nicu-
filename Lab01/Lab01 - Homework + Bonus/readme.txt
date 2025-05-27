Acest ReadMe este pentru homework 1 si bonus 1.

Homework 1:

In cadrul temei am ales sa rezolv problema prin a impune existenta unei clica si unui stable set de minim k.
Ca acest lucru sa fie posibil trebuie ca n sa fie minim 2k. Clica este un subgraf complet si stable set este o submultime de noduri ce nu au nicio muchie intre ele. Pentru a avea ambele trebuie sa am 2 submultimi de noduri egale cu k diferite, de acolo 2k.
Dupa aceea pentru a asigura randomizarea grafului am ales sa creez un array de marime n si sa ii dau shuffle, ptr a da shuffle am luat array de la coada si am inceput sa dau swapuri prin el folosind un random.
Apoi voi lua primele k elemente din array dupa shuffle si vor apartine unui vector clica, urmatoarele k apartin stable set (matricea este initializata de la 0, asa ca nu ma voi atinge de ele), iar tot ce ramane vor primi muchii random in tot graful. 
Pentru grad maxim si minim calculez gradul fiecarui nod si compar cu un mini si maxi. 
De asemenea pentru suma gradelor de fiecare data cand intalnesc un 1 in matrice, cresc degreeSum. 
Pentru nr de muchii impart suma gradelor la 2. (Exista o formula: 2*m = SIGMA[grade]).
Pentru timpul de rulare ptr un graf mare la inceput creez un long cu timpul in nanosecunde de start, cand intalnesc un graf prea mare creez inca un long, si apoi scad cel de start din cel de end. (Am setat n>50 deoarece eu afisez si clica si stable set si array dupa shuffle, imi dadea peste cap afisarea).

Bonus 1:

Am 2 functii una numita bonus ce ia din codul mare n, k si matricea, iar a doua este un backtracking.
Functia bonus gaseste potentiali candidate pentru a fi trimisi in backtracking unde se va cauta clica da marime k.
Cum se cauta candidatii? Verifica fiecare nod din graf si daca acesta are grad mai mare sau egal decat k-1 acesta este candidat.
Apoi apelam BKT cu candidatul respectiv, k si graful.
BKT este un algoritm recursiv ce cauta o clica de minim marime k pe baza matricei de adiacenta.
Cum actioneaza acesta? Intai verifica sa nu fim out of bound cu indexul. Apoi daca acest rezultat este egal cu marimea clicai atunci incep sa o afisez pe aceasta, in caz contrar incep sa ma duc mai adanc cu backtracking-ul pana cand acesta nu mai este posibil.