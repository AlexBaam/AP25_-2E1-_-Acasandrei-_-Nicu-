Grupa 2A2:
Echipa:
Acasandrei Nicu 
Bostan Georgiana

In cadrul acestui laborator am creat un joc cu scopul de a uni puncte pana cand structura de pe ecran este conexa.
Jocul este intre 2 jucatori, iar fiecare jucator primeste un scor bazat pe distanta euclidiana dintre cele 2 puncte.
Jucatorul cu scor mai mic castiga.

Cum functioneaza:

1. MainFrame este clasa ce creaza in sine interfata in care este deschisa aplicatia, mai exact box-ul pe care vor fi puse
butoanele si canva-ul pe care se va desena.

2. ConfigPanel este clasa in care se creaza butonul de New Game si care apeleaza clasa NewGame pentru setarile din acesta.
De asemenea tot in ConfigPanel este creat un label, si pus sub butonul de new game. Scopul acestuia este sa arate a
carui jucator ii este randul.

In clasa NewGame avem 2 comboBox-uri si un spinner.
Spinnerul ne permite sa setam numarul de puncte, iar cele 2 comboBox-uri sunt pentru a alege tipul de player pentru player2,
respectiv dificultatea pentru AI in cazul in care planuim sa jucam impotriva unuia.

3. ControlPanel este o clasa folosita pentru a crea cele 2 butoane de jos, History si Settings.
Butonul de history apeleaza clasa GameHistory in event actionul sau pentru a crea o fereastra in care este afisata o lista de
string-uri ce reprezinta scorurile obtinute de fiecare jucator pe parcursul jocului.
Pentru butonul de settings este total creat in ControlPanel, acesta este un dialogBox ce contine alte 4 butoane, mai exact:
Save: Salveaza starea actuala a jocului intr-un fisier numit "game_state.ser"
Ce inseamna sa salveze starea jocului?
- Locatiile punctelor
- 2 liste pentru liniile rosii, respectiv albastre
- scorurile jucatorilor
- daca jucam impotriva AI sau nu
- dificultatea AI
- a carui jucator este runda actuala
Load: Reda toate informatiile salvate in fisierul mentionat anterior catre joc, pe scurt actioneaza ca un constructor
asupra intregului joc
Export: Creaza o imagine PNG a canva-ului cu starea sa curenta
Exit: Inchide jocul

4. DrawingPanel este clasa ce creaza canva-ul pe care sunt generate punctele, dar se si petrece tot jocul.
Ce se intampla in acesta?
- se creaza vizual spatiul pentru joc, dar si un buffer ce ajuta pentru a avea un retained mode al jocului
(si liniile si punctele apar vizual, dar sunt salvate si in memorie, asta permitand optiunilor de save si load sa existe)
- actiunile mouse-ului sunt definite tot in aceasta clasa, mai exact se verifica daca este dat click pe un punct,
apoi se fac verificari pe punct, pe jucator si pe urmatorul punct, astfel incat sa deseneze corect o linie
- gameOver este o metoda ce incheie jocul, setand "isGameOver" pe true, si afisand scorul final
- resetGame se ocupa de repornirea jocului atunci cand se apasa newGame, recreand canva-ul pe care se joaca, bazat pe noile
setari primite
- drawLineAi se ocupa de desenat liniile vizual pentru AI atunci cand jucatorul 2 este setat ca AI

Alte clase:
1. Line, este o clasa in care este definit conceptul de linie
2. Scoreboard, este clasa in care sunt initializate si updatate scorurile jucatorilor (pentru calcul este realizat in GameLogic)
3. GameLogic, este o clasa folosita pentru realizarea logicii jocului, adica calculare de scoruri, calcularea distantei euclidiene,
resetarea acestor scoruri, dar si crearea listei ce va fi afisata in caseta de history
4. GraphForGame, este o clasa ce creaza graful jocului pe care il vom tot verifica daca este sau nu conex, iar atunci cand este conex,
oferim semnalul de incheiere a jocului

Pentru bonus:

1. GameAI, este clasa in care cream metoda de jucat a AI-ului, mai exact selectandu-i dificultatea si apoi pe baza acesteia,
la fiecare runda noua cream un set de spanning trees dintre care alegem unul (bazat pe dificultate) apoi cautam o muchie in acesta si
daca aceasta nu exista deja, o desenam

2. SpanningTreeGenerator, este clasa ce se ocupa de crearea multiplilor spanning trees si sortarea acestora bazat pe costul total,
astfel incat cel cu cel mai mic cost total este cel optim folosit pentru dificultatea HARD, unul din mijlocul listei este pentru MEDIUM,
iar pentru easy folosim ultimul

3. O aditie personala este GameSound ce este o clasa ce se ocupa de cautarea unor sunete de tip .wav intr-un fisier din acest proiect si
le da play atunci cand actiunea asociata are loc