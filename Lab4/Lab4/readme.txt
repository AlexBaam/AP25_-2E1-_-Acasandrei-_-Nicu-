Cum functioneaza codul?
Pentru saptaman asta a trebuit sa restructurez in mare parte codul pentru a crea randomizarile cerute la bonus.

	Clasa Location.java:

-Aceasta are nume random oferit cu faker, ce primeste firstName de om (David, Marian, sau ce vrea el pe acolo). 
-Genereaza un code name random folosind clasa CodeName.
-Pentru tipul de locatie am facut un vector de LocationType ce contine toate tipurile de locatie in el, apoi ofer una random cu obiectul de tip random.
-Alte metode sunt getType, getCodeName, toString() ce sunt destul de self explanatory.
-Tin sa mentionez ca locationType este un Enum ce contine "FRIENDLY, NEUTRAL, ENEMY".
	
	Clasa CodeName:

-In location noi cand apelam constructorul ii oferim un vector de stringuri, iar acesta foloseste o mapare de string cu
int pentru a oferi fiecarui string numarul de aparitii, ce mai apoi este atasat la acesta si devine codeName.

	Clasa LocationGenerator:

-Avem un constructor in care noi oferim numarul de noduri (locatii) pentru graful ce va fi creat.
-Fiind graf nedirectionat am decis sa folosesc formula n*(n-1)/2 pentru a calcula nr maxim posibil de muchii, apoi generez un nr random intre nrMax/4 si nrMax.
-Metoda locationGenerate va face un for de la 0 la nr de noduri-1 si va crea cate o noua locatie cu un codeName generat aleatoriu, apoi acea locatie va fi pusa intr-o lista de locatii.
-Ignoram printAllLocations(), a existat ptr debug.
-Metoda createGraph doar trimite fiecare locatie din lista si o adauga in graf.
-Metoda generateConnection va genera o conexiune random ptr nr de muchii calculate mai sus.
-Toate celelalte metode sunt pentru verificari daca graful este functional conform randomizarii.

	Clasa LocationGraph:

Aici construim graful, cum o facem?
-addLocation va adauga o locatie data ca vertex (nod).
-addConnection verifica ca locatiile date sa nu fie aceleasi, daca nu sunt, atunci atunci adauga muchii in ambele parti fiindca e graf orientat, si un weight pe muchie ptr Dijkstra.
-Weight este luat random, depinzand de tipul locatiei, FRIENDLY = 1,2,3; ENEMY = 7,8,9;
-addRandomConnection randomizeaza alegerea de locatii intre care se va adauga muchie prin 2 for-uri, si weight ptr muchii.
-getFastestRoute va calcula cel mai rapid drum de la o locatie de start oferita de noi pana la orice alta locatie din graf folosind Dijkstra, atat timp cat acestea sunt diferite;
-Folosind aceasi metoda afisam si weight, dar si drumul parcurs de Dijkstra;
-getFastestRouteBetween o sa calculeze cel mai rapid drum cu Dijkstra intre 2 locatii oferite de noi;
-Aceasta metoda este folosita pentru a cacula statisticile cerute la final;
-Metodele countTypes si computeSafestPaths sunt folosite pentru a Numara prin cate locatii de un tip trecem, si a calcula cel mai sigur drum de la o locatie la toate locatiile oferite in graf, si afisam prin cate locatii de un tip trecep pe acel drum.
-averageFastestPath a este ultima cerinta din bonus ce calculeaza cele mai rapide drumuri din tot graful, le salveaza intr-o lista apoi calculam un timp average pentru a ajunge dintr-un nod in altul, atat timp cat lista nu este nula, altfel primim 0 (orElse(0.0));
-De asemenea tot in aceasta metoda afisam weightul maxim ce exista in lista noastra de la o muchie la alta, weight minim (este 1 tot timpul);
-Deoarece nr de muchii generate este random, afisam nr maxim posibil de muchii, nr de muchii generate, si nr de muchii lipsa;