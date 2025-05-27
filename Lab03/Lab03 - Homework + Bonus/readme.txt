Acest fisier contine si tema si bonusul. Intregul program este realizat in cadrul mai multor clase.
Clasa ce descrie si rezolva problema este Manager, in aceasta fiind prezente solver si bonusSolver.
Metoda solver functioneaza asa:

Cream o lista de zboruri ce va contine toate zborurile din aeroport, pe care mai apoi le sortam cu ajutorul unui comparator pe baza primei ore din schedule Pair.
Apoi iteram prin lista si fiecare zbor intai este notat ca fals intr-un Boolean de scheduled.
Din nou incepem o noua iteratie prin lista de piste de zbor, si apelam metoda "canAddFlight" ce verifica daca un zbor poate fi adaugat pe o pista, daca da, atunci va fi adaugat in acea lista, va fi mapat si trecut ca true la scheduled.
La final afisam ce zboruri nu au putut fi asignate unei piste.

Metoda bonusSolver functioneaza asa:

Apelam clasa FlightGraph ce creaza o matrice de adiacenta pentru zborurile cu conflict intre ele (ptr a ne asigura ca nu avem conflicte intre un zbor si el insusi setam diagonala principala cu 0).
Apoi creez o mapa numita assignedRunways pe care apelez metoda dSaturColoring.
dSaturColoring este un algorithm ce itereaza prin matricea de adiacenta si verifica conflictele asigurandu-se ca poate pune orice 2 noduri legate in runways diferite, ca o colorare pe un graf in care oferim culori diferite oricaror 2 noduri legate.
La final verificam daca runwayIndex este mai mare sau egal cu nr de runways dintr-un aeroport afisam faptul ca nu avem destule piste ptr zborul ce vrem sa il adaugam, otherwise zborul va fi adaugat si la final printam lista.