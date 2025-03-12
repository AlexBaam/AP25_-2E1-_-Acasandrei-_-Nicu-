In main creez obiecte de tip proiect, le adaug in lista profesorului de proiecte.
Creez vector de proiecte preferate de student pentru folosirea in rezolvarea problemei.
Creez obiecte de tip student, professor, problema si solutie.

Clasa persoana este o clasa parinte pentru student si professor.
Cele 2 clase copil nu sunt foarte speciale, ele sunt folosite in clasa problema.
Clasa proiect este folosita in clasa professor si problema, obiectele de tip proiect trebuie impartite la studenti.

Clasa problema se foloseste de arrays de tip student, professor, proiect si persoana. 
Aceasta poate adauga elemente in aceste arrays, si sa le printeze.

Clasa Solution este cea mai relevanta, aceasta implementeaza o metoda bazata pe un algorithm greedy ce aloca proiecte studentilor.
Aceasta metoda se numeste solve(), si pentru a functiona se foloseste de un array de Booleans in care verifica daca un proiect a fost alocat sau nu.
Vom itera prin numarul maxim de studenti si intr-un array de tip proiect vom pune proiectele lor ce le-ar prefera, apoi vom itera prin acest array. In timp ce iteram vom lua indexul pentru fiecare proiect preferat si verificam daca acel proiect nu a fost alocat deja, daca nu a fost, il dam acelui student si il setam in array Boolean drept true.
Acest algorithm poate sa lase studenti fara proiect deoarece cauta prima solutie, nu cea mai buna.

Tot in clasa Solution am bonusul ce este o metoda numita SolveHall() ce foloseste o alta metoda private acestei clase numita checkHellTheorem(). SolveHall() doar verifica daca checkHallTheorem returneaza true sau false.
Teorema lui Hall spune |N(S)| >= |S|, aceasta se aplica pe grafuri bipartite si spune asa: Ca pentru orice subset de noduri, cardinalul vecinatatii acestui subset trebuie sa fie cel putin egal cu nr de noduri din subset, altfel nu putem avea un cuplaj.
Ce este un cuplaj? Un cuplaj este o submultime de muchii astfel incat fiecare nod din submultime sa aiba o muchie incidenta. 
Cum se aplica asta pe problema noastra? Daca subsetul de varfuri ar fi format din studenti, atunci ca fiecare student sa poata primi un proiect trebuie sa multimea de proiecte preferate ale acelui subset sa fie cel putin egala cu nr de studenti. Adica pentru X1 un subset doar din S1, acesta are 2 proiecte preferate. |N(S)| = 2, |S| = 1, verifica teorema lui Hall, se poate face alocarea, return true. 
Dar pentru un exemplu cu X2 din S1,S2,S3 si toti prefera problema P1, atunci |N(S)| = 1 si |S| = 3, atunci nu putem aloca 3 proiecte diferite la cei 3 studenti deoarece nu exista cuplajul posibil. 
Cum implementam aceasta conditie si o verificam? Pai iteram prin toate grupurile de studenti (in cazul nostrum fiecare student este singur, fiind mult mai usor) si le verificam preferintele, daca nr de studenti este mai mic sau egal cu nr de proiecte diferite ce pot fi preferate de acestia, atunci cuplajul este posibil. 
Cum aflam nr de proiecte diferite? Iteram prin vectorii de preferite a fiecarui student/ grupare de studenti, daca un proiect a mai fost intalnit, este deja true, daca nu devine true si o variabila differentProjects creste. Daca nr total de studenti este mai mic sau egal cu different students atunci exista un cuplaj prefect. 
A doua cerinta pentru acest bonus este sa verific acest algorithm pentru un numar mare de posibile proiecte si studenti, si sa analizez timpul si memoria folosite. La data 12.03.2025 cand dau push inca nu am implementat, urmeaza pana la final de saptamana si sa dau update.