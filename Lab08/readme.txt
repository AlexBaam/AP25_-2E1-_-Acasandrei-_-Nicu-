In cadrul laboratorului 8 am realizat o conexiune cu o baza de date de tip PostgreSQL creata prin pgAdmin, ce contine urmatoarele tabele:
- Continents;
- Countries;
- Cities;

Aceste tabele contin informatii precum ID si nume ale unor continente, tari si orase, ce sunt legate intre ele (Un oras apartine unei tari, o tara unui continent).
In compulsory tot ce se intamlpa este crearea acestor tabele, crearea unei conexiuni prin Database.java, si punerea informatiei in tabele prin intermediul claselor:
- ContinentDAO;
- ContryDAO;
- CityDAO; 
Acestea se realizeaza cu metoda create. 

In cadrul homework inlocuim clasa database.java cu clasa HikariDatabase.java ce permite crearea unui conection pool mai usor avand metode predefinite.
Pasul 2 al temei este crearea unui model OOP al tabelelor, adica clasele Continent, Country si City, unde un obiect reprezinta o instanta din tabel.
Urmatorul pas este utilizarea unui tool precum WorldCapitalsGPS pentru a importa informatii despre capitalele lumii, dar pentru a usura munca, folosim fisierul cities.csv 
unde am definit un format simplu al oraselor ce pot fi puse in acesta. Din acesta extragem informatia folosind clasa CityImporter, in care are loc citirea din fisierul csv, 
apoi vom parsa informatia din acesta, cautam codul continentului si cream noile instante.
Pentru calculul distantei folosim clasa DistanceComputer in care doar aplicam formula matematica ce poate fi gasita pe W3School, chiar in format java.

In cadrul bonus8 ceea ce ne este cerut este introducerea in tabelul cities a peste 10000 de orase generate automat, iar pentru asta folosim clasa CityGenerator in care tot
ce se intampla este apelarea comenzii create a clasei CityDAO de numarul nostru de ori, dar ptr fiecare oras se genereaza un nume fals cu faker, si o longitudine si latitudine
random folosind rand. De asemenea niciunul nu va fi capitala iar tara carora apartin este random folosind un nextInt.

Dupa generarea oraselor cream un nou tabel numit SisterCities ce are drept cheie primara id-ul a doua orase ce sunt surori, iar pentru asta doar iteram prin orase si cu o probabilitate
foarte mica le punem id-urile in tabela. Pentru a insera in tabela folosim o functie din SisterCitiesDAO, ce se numeste insert, si este acelasi lucru cu create din celelalte.
Iar pentru a obtine aceste conexiuni ce ne trebuie pentru generarea grafului, mapam cele doua locatii in functia getAllRelations.
Apoi folosim JgraphsT pentru a genera un graf ce are orasele ca noduri si relatiile ca muchii, dar nu am reusit sa rezolv cerinta de a obtine un set maximal de 2-connectivitate, 
fiindca nu stiu grafuri destul de bine. 1 - 0 ptr grafuri. 
Ultima cerinta este generarea unei imagini cu graful, ce este realizata in ImageGenerator, unde iterez printre muchiile grafului, apoi le desenez cu gri, iar orasele le reprezint cu rosu.
