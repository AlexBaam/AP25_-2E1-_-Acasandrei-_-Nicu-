Game
-clasa principala care coordoneaza jocul
-contine logica de sincronizare intre jucatori
-porneste thread-urile pentru jucatori si pentru TimeKeeper
-metoda play() porneste jocul
-metoda stopGame() opreste jocul si intrerupe jucatorii

Player
-reprezinta un jucator care ruleaza intr-un thread separat (Runnable)
-fiecare jucator isi asteapta randul folosind wait() si continua cu notifyAll()
-genereaza cuvinte valide folosind backtracking (bkt())
-se opreste daca nu mai sunt litere sau este intrerupt de TimeKeeper
scorul se calculeaza in functie de literele folosite

Dictionary
-incarca cuvinte dintr-un fisier text (words.txt)
-salveaza cuvintele intr-un HashSet pentru acces rapid
-metoda isWord() verifica daca un cuvant este valid

Bag
-contine toate piesele de joc (Tile) – fiecare litera apare de 10 ori
-punctajele sunt generate aleator intre 1 si 10
-metoda sincronizata extractTiles() permite extragerea sigura a pieselor
-verifica daca sacul mai are litere disponibile

TimeKeeper
-thread de tip daemon care cronometreaza jocul
-daca timpul depaseste limita (ex: 30 secunde), jocul se opreste
-verifica daca toti jucatorii au terminat
-afiseaza timpul trecut la fiecare secunda
-anunta castigatorul in functie de scor