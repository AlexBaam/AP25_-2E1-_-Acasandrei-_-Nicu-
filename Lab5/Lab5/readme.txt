Tema + Bonus Laboratorul5:

Echipa: Bostan Georgiana, Acasandrei Nicu

Pentru cerinta de shell folosim o clasa shell cu un scanner intr-un while(true) 
pentru a imita comportamentul unui shell real.

Comenzile implementate la cerinta 2 sunt asa:
Exit: Inchidere while;
Add: Adaugarea unei singure imagini bazata pe nume si path;
AddAll: Adaugarea tuturor imaginilor existente in directorul de imagini si subdirectoarele acestuia - bonus;
Remove: Stergerea unei singure imagini baza pe numele acesteia;
Format: Load & Save bazate pe alegerea unui format (JSON, Binary, Text) - bonus; //Folosim Jackson ptr JSON cu serializarea unei liste de imagini;
Update: Updatarea informatiei unei imagini din repository;
Report: Crearea unui HTML in care se afla imaginile din repository;
Display: Afisarea unei imagini;

Pentru cerinta 3 exceptiile custom adaugate sunt:
InvalidCommandException
InvalidDataException
InvalidFormatException

Acestea au ca scop semnalarea introducerii unei comenzi gresite in shell, a unui tip gresit de date in save/load
sau introducerea unui format gresit in comanda de selectare format.

Ultima cerinta de la homework cu crearea unui jar executabil a fost realizata prin urmatorii pasi:
1. Te duci la File -> ProjectStructure -> Artifacts -> *CLICK PLUS* -> JAR -> From modules with dependencies -> Selectezi Main Class
2. Te duci la Build -> Build Artifacts... -> name.JAR -> Build
Apoi pentru a rula trebuie sa te duci in folderul unde se afla acel JAR si accesezi command Prompt in care executi urmatoarea comanda:
java -jar name.JAR 
JAR-ul ar trebui sa ruleze si sa poata fi accesat shellul realizat.

Pentru cerintele din bonus am realizat asa:
1. O comanda format ce trimite user-ul intr-o clasa ce permite selectarea unuia dintre cele 3 formate, mai apoi daca acesta vrea sa salveze sau sa dea load.
Pentru fiecare comanda dintre save/load am implementat o varianta diferita a acesteia depinzand de format.
Informatiile vor fi salvate intr-un director din root numit "data". Acest pas este realizat pentru a putea folosi relative path-ul acestor spatii de salvare.

2. O comanda addAll ce poate fi accesata din shell si se va duce in "images" un folder din root (din nou ptr path relativ) 
ce va itera prin toate fisierele din acest folder si va verifica "este file sau director", si daca este file verifica extensia, iar daca aceasta este de imagine adauga in repository,
altfel daca este director, va apela din nou functie de verificare director recursiv pe noul director gasit, 
si tot asa pana trecem prin tot ce exista in images.

3. Pentru generarea de tag-uri random nu ne-am complicat, atunci cand o imagine este adaugata manual 
sau de addAll aceasta va primi cu un Random rand un set de tag-uri dintr-o lista.

4. Inca lucram la acest pas.
