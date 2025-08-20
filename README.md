*english version below*
## Esercizio 1

### Linguaggio richiesto: C

### Testo

Implementare una libreria che offre due algoritmi di ordinamento `Quick Sort` e `Binary Insertion Sort`. Con `Binary Insertion Sort` ci riferiamo a una versione dell'algoritmo `Insertion Sort` in cui la posizione all'interno della sezione ordinata del vettore in cui inserire l'elemento corrente è determinata tramite ricerca binaria. Nell'implementazione del `Quick Sort`, la scelta del `pivot` dovrà essere studiato e discusso nella relazione.

Il codice che implementa `Quick Sort` e `Binary Insertion Sort` deve essere generico. Inoltre, la libreria deve permettere di specificare (cioè deve accettare in input) il criterio secondo cui ordinare i dati.

### Unit Testing

Implementare gli unit-test per la libreria secondo le indicazioni suggerite nel documento Unit Testing.

### Uso della libreria di ordinamento implementata

Il file `records.csv` che potete trovare (compresso) all'indirizzo

```
https://datacloud.di.unito.it/index.php/s/X7qC8JSLNRtLxPC
```

contiene 20 milioni di record da ordinare.
Ogni record è descritto su una riga e contiene i seguenti campi:

- id: (tipo intero) identificatore univoco del record;
- field1: (tipo stringa) contiene parole estratte dalla divina commedia,
  potete assumere che i valori non contengano spazi o virgole;
- field2: (tipo intero);
- field3: (tipo floating point);

Il formato è un CSV standard: i campi sono separati da virgole; i record sono
separati da `\n`.

Usando gli algoritmi `Quick Sort` e `Binary Insertion Sort` implementati, si ordinino i *record* (non è sufficiente ordinare i
singoli campi) contenuti nel file `records.csv` in ordine non decrescente secondo i valori contenuti nei tre campi "field" (pertanto, è necessario ripetere l'ordinamento tre volte, una volta per ciascun campo).

Si misurino i tempi di risposta variando il criterio di scelta del `pivot` nel `Quick Sort` e si produca una breve relazione in cui si riportano i risultati ottenuti insieme a un loro commento. Nel caso l'ordinamento si protragga per più di 10 minuti potete interrompere l'esecuzione e riportare un fallimento dell'operazione.

I risultati sono quelli che vi sareste aspettati? Se sì, perché? Se no, fate delle ipotesi circa il motivo per cui gli algoritmi non funzionano come vi aspettate, un algoritmo offre delle prestazioni migliori dell'altro, la scelta del `pivot` influenza le prestazioni di `Quick Sort`. Verificatele e riportate quanto scoperto nella relazione.

**Si ricorda che** che il file `records.csv` **NON DEVE ESSERE OGGETTO DI COMMIT SU GIT!**

## Esercizio 2 - Skip List

### Linguaggio richiesto: C

### Testo

Realizzare una struttura dati chiamata `skip_list`. La `skip_list` è un tipo di lista concatenata che memorizza una *lista ordinata* di elementi.

Al contrario delle liste concatenate classiche, la `skip_list` è una struttura dati probabilistica che permette di realizzare l'operazione di ricerca con complessità `O(log n)` in termini di tempo. Anche le operazioni di inserimento e cancellazione di elementi possono essere realizzate in tempo `O(log n)`. Per questa ragione, la `skip_list` è una delle strutture dati che vengono spesso utilizzate per indicizzare dati.

Ogni nodo di una lista concatenata contiene un puntatore all'elemento successivo nella lista. Dobbiamo quindi scorrere la lista sequenzialmente per trovare un elemento nella lista. La `skip_list` velocizza l'operazione di ricerca creando delle "vie espresse" che permettono di saltare parte della lista durante l'operazione di ricerca. Questo è possibile perché ogni nodo della `skip_list` contiene non solo un singolo puntatore al prossimo elemento della lista, ma un array di puntatori che ci permettono di saltare a diversi punti seguenti nella lista. Un esempio di questo schema è rappresentato nella seguente figura:

![Esempio di una `skip_list`. Dal nodo che contiene il numero 6 si può saltare direttamente ai nodi 9 e 25, senza visitare gli altri nodi.](skiplist.png)

Si implementi quindi una libreria che realizza la struttura dati `skip_list`. L'implementazione deve essere generica per quanto riguarda il tipo dei dati memorizzati nella struttura. Come suggerimento, una possibile definizione del tipo di dati `skip_list` è la seguente:

```
#define MAX_HEIGHT ....

typedef struct _SkipList SkipList;
typedef struct _Node Node;

struct _SkipList {
  Node *head;
  unsigned int max_level;
  int (*compare)(void*, void*);
};

struct _Node {
  Node **next;
  unsigned int size;
  void *item;
};
```

Dove:

- `MAX_HEIGHT`: è una costante che definisce il massimo numero di puntatori che possono esserci in un singolo nodo della `skip_list`. Come si vede nella figura, ogni nodo può avere un numero distinto di puntatori.

- `unsigned int size`: è il numero di puntatori in un dato nodo della `skip_list`.

- `unsigned int max_level`: determina il massimo attuale tra i vari `size`.

La libreria deve includere le due operazioni elencate sotto, che sono riportate in pseudo-codice. Tradurre lo pseudo-codice in C.

##### insertSkipList: Inserisce I nella skiplist ``list``
```
insertSkipList(list, I)

    new = createNode(I, randomLevel())
    if new->size > list->max_level
        list->max_level = new->size

    x = list->head
    for k = list->max_level downto 1 do
        if (x->next[k] == NULL || I < x->next[k]->item)
            if k < new->size {
              new->next[k] = x->next[k]
              x->next[k] = new
            }
        else
            x = x->next[k]
```

La funzione ``randomLevel()`` determina il numero di puntatori da includere nel nuovo nodo e deve essere realizzata conformemente al seguente algoritmo. Spiegare il vantaggio di questo algoritmo nella relazione da consegnare con l'esercizio:
```
randomLevel()
    lvl = 1

    // random() returns a random value in [0...1)
    while random() < 0.5 and lvl < MAX_HEIGHT do
        lvl = lvl + 1
    return lvl
```

#####  searchSkipList: Verifica se I è presente nella skiplist list
```
searchSkipList(list, I)
    x = list->head

    // loop invariant: x->item < I
    for i = list->max_level downto 1 do
        while x->next[i]->item < I do
            x = x->next[i]

    // x->item < I <= x->next[1]->item
    x = x->next[1]
    if x->item == item then
        return x->item
    else
        return failure
```


La libreria deve anche includere delle funzioni che permettono di creare una `skip_list` vuota e cancellare completamente una `skip_list` esistente. Quest'ultima operazione, in particolare, deve liberare correttamente tutta la memoria allocata per la `skip_list`.


### Unit Testing

Implementare gli unit-test per tutte le operazioni della `skip_list` secondo le indicazioni suggerite nel documento Unit Testing.

### Uso delle funzioni implementate

All'indirizzo

```
https://datacloud.di.unito.it/index.php/s/taii8aA8rNnXgCN
```
potete trovare un dizionario (`dictionary.txt`) e un file da correggere (`correctme.txt`).

Il dizionario contiene un elenco di parole. Le parole sono scritte di seguito, ciascuna su una riga.

Il file `correctme.txt` contiene un testo da correggere. Alcune parole in questo testo non ci sono nel dizionario.

Si implementi una applicazione che usa la struttura dati ``skip_list`` per determinare in maniera efficiente la lista di parole nel testo da correggere non presenti nel dizionario dato come input al programma.

Si sperimenti il funzionamento dell'applicazione considerando diversi valori per la constante ``MAX_HEIGHT``, riportando in una breve relazione (circa una pagina) i risultati degli esperimenti.

**Si ricorda** che il dizionario e `correctme.txt` **NON DEVONO ESSERE OGGETTO DI COMMIT SU GIT!**

### Condizioni per la consegna:

-- Creare una sottocartella chiamata ``ex2`` all'interno del repository.

-- La consegna deve obbligatoriamente contenere un `Makefile`. Il `Makefile` deve produrre all'interno di ``ex2/build`` un file eseguibile chiamato ``main_ex2``.

-- ``main_ex2`` deve ricevere come parametri il path del dizionario da usare come riferimento e il file da correggere, necessariamente in quest'ordine. Il risultato va stampato a schermo, con le parole ordinate come nel file da correggere. Per esempio:

```
$ ./main_ex2 /tmp/data/dictionary.txt /tmp/data/correctme.txt 
cinqve
perpeteva
squola
domandrono
vuolessi
scrissi
corpito
wita
```

## Esercizio 3

### Linguaggio richiesto: Java

### Testo

Si implementi una libreria che realizza la struttura dati Heap Minimo. La struttura dati deve:
- rappresentare internamente lo heap tramite un vettore (è possibile usare anche altre strutture interne di supporto, se necessarie);
- consentire un numero qualunque e non noto a priori di elementi dello heap;
- essere generica per quanto riguarda il tipo degli elementi dello heap;
- essere generica per quanto riguarda il criterio di confronto fra elementi dello heap.

Essa deve, inoltre, offrire (almeno) le seguenti operazioni (accanto a ogni operazione è specificata la
complessità richiesta, in cui n indica il numero di elementi dello heap):
- creazione di uno heap minimo vuoto - O(1);
- inserimento di un elemento - O(log n);
- restituzione della dimensione dello heap - O(1);
- restituzione del genitore di un elemento - O(1);
- restituzione del figlio sinistro di un elemento - O(1);
- restituzione del figlio destro di un elemento - O(1);
- estrazione dell'elemento con valore minimo - O(log n);
- diminuzione del valore di un elemento - O(log n).

Una descrizione della struttura dati Heap è riportata sui lucidi e le dispense fornite nella parte di teoria del corso,
 nonché sul testo Cormen et al, `Introduzione agli algoritmi e strutture dati`, McGraw-Hill, Terza edizione, 2010, nel capitolo `Heapsort`. In particolare, si suggerisce il riferimento al testo per tutti quegli aspetti non esplicitamente trattati a lezione.

### Unit Testing

Implementare gli unit-test per la libreria secondo le indicazioni suggerite nel documento Unit Testing.

## Esercizio 4

### Linguaggio richiesto: Java

### Testo

Si implementi una libreria che realizza la struttura dati Grafo in modo che **sia ottimale per dati sparsi**
(IMPORTANTE: le scelte implementative che farete dovranno essere giustificate in relazione alle nozioni presentate
durante le lezioni in aula). La struttura deve consentire di rappresentare sia grafi diretti che grafi non diretti
(suggerimento:  un grafo non diretto può essere rappresentato usando un'implementazione per grafi diretti modificata
per garantire che, per ogni arco (a,b), etichettato w, presente nel grafo, sia presente nel grafo anche l'arco (b,a),
etichettato w. Ovviamente, il grafo dovrà mantenere l'informazione che specifica se esso è un grafo diretto o non diretto.).

L'implementazione deve essere generica sia per quanto riguarda il tipo dei nodi, sia per quanto riguarda le etichette
degli archi.

La struttura dati implementata dovrà offrire (almeno) le seguenti operazioni (accanto a ogni operazione è specificata la
complessità richiesta; n può indicare il numero di nodi o il numero di archi, a seconda del contesto):

- Creazione di un grafo vuoto – O(1)
- Aggiunta di un nodo – O(1)
- Aggiunta di un arco – O(1)
- Verifica se il grafo è diretto – O(1)
- Verifica se il grafo contiene un dato nodo – O(1)
- Verifica se il grafo contiene un dato arco – O(1)  (*)
- Cancellazione di un nodo – O(n)
- Cancellazione di un arco – O(1)  (*)
- Determinazione del numero di nodi – O(1)
- Determinazione del numero di archi – O(n)
- Recupero dei nodi del grafo – O(n)
- Recupero degli archi del grafo – O(n)
- Recupero nodi adiacenti di un dato nodo – O(1)  (*)
- Recupero etichetta associata a una coppia di nodi – O(1) (*)

(*) quando il grafo è veramente sparso, assumendo che l'operazione venga effettuata su un nodo la cui lista di adiacenza ha una lunghezza in O(1).

### Unit Testing

Implementare gli unit-test per la libreria secondo le indicazioni suggerite nel documento Unit Testing.

### Uso delle librerie che implementano la struttura dati Grafo e la struttura dati Heap

Si implementi l'algoritmo di Dijkstra per determinare i cammini minimi da sorgente unica in un grafo orientato pesato, con pesi degli archi tutti non negativi.

L'implementazione dell'algoritmo di Dijkstra dovrà operare su un grafo realizzato tramite la libreria implementata secondo le specifiche fornite sopra e dovrà inoltre utilizzare al proprio interno una coda di priorità minima rappresentata con un heap realizzato con la libreria implementata secondo le specifiche dell'Esercizio 3.


### Uso della libreria che implementa la struttura dati Grafo e dell'algoritmo di Dijkstra

Si scriva un'applicazione che utilizza l'algoritmo di Dijkstra implementato per determinare i cammini minimi dalla città di Torino sul grafo descritto nel file italian\_dist\_graph.csv che potete trovare (compresso) all'indirizzo

```
https://datacloud.di.unito.it/index.php/s/PirTJpq4JMnpH3G
```

Tale file contiene le distanze in metri tra alcune località
italiane e una frazione delle località a loro più vicine.
Il formato è un CSV standard: i campi sono separati da virgole; i record sono separati dal carattere di fine
riga (\\n).

Ogni record contiene i seguenti dati:

- località 1: (tipo stringa) nome della località "sorgente". La stringa può contenere spazi, non può contenere virgole;
- località 2: (tipo stringa) nome della località "destinazione". La stringa  può contenere spazi, non può contenere virgole;
- distanza: (tipo float) distanza in metri tra le due località.

**Note** :

- potete interpretare le informazioni presenti nelle righe del file come   archi non diretti (pertanto, si suggerisce di inserire nel grafo sia l'arco di andata che quello di ritorno a fronte di ogni riga letta).
- il file è stato creato a partire da un dataset poco accurato. I dati riportati contengono inesattezze e imprecisioni.

**Si ricorda** il file italian\_dist\_graph.csv **NON DEVE ESSERE OGGETTO DI COMMIT SU GIT!**

**Controlli**

Un'implementazione corretta dell'algoritmo di Dijkstra, eseguita sui dati
contenuti nel file italian\_dist\_graph.csv, dovrebbe determinare un cammino
minimo tra "torino" e "catania" lungo ~1207.68 Km.


---
### Exercise 1  

### Required Language: C  

---

### Task  

Implement a library that provides two sorting algorithms: `Quick Sort` and `Binary Insertion Sort`.  
By `Binary Insertion Sort`, we mean a variant of the `Insertion Sort` algorithm where the insertion position of the current element within the sorted part of the array is determined using **binary search**.  

For the `Quick Sort` implementation, the strategy for choosing the `pivot` must be studied and discussed in the final report.  

The code implementing both `Quick Sort` and `Binary Insertion Sort` must be **generic**. Additionally, the library must allow the specification of a custom comparison criterion (passed as input).  

---

### Unit Testing  

Implement unit tests for the library according to the guidelines provided in the **Unit Testing** document.  

---

### Usage of the Sorting Library  

The file `records.csv` (compressed and available at the link below):  

```
https://datacloud.di.unito.it/index.php/s/X7qC8JSLNRtLxPC
```


contains **20 million records** to be sorted.  

Each record is stored on a single line and includes the following fields:  

- **id**: (integer) unique record identifier  
- **field1**: (string) contains words extracted from *Divine Comedy* (values contain neither spaces nor commas)  
- **field2**: (integer)  
- **field3**: (floating point)  

The format is standard CSV: fields are separated by commas, records are separated by `\n`.  

Using the implemented `Quick Sort` and `Binary Insertion Sort`, sort the *records* (not just single fields) in non-decreasing order based on the values in each of the three "field" columns.  
This means the sorting must be performed **three times**: once for `field1`, once for `field2`, and once for `field3`.  

Measure execution times while varying the pivot selection strategy in `Quick Sort`, and write a short report describing the results and your analysis.  
If sorting takes longer than **10 minutes**, you may interrupt the execution and report the operation as failed.  

Questions to address in the report:  

- Are the results what you expected? Why or why not?  
- If not, what hypotheses can you make about the discrepancies?  
- Does one algorithm perform better than the other?  
- How does the pivot selection strategy affect `Quick Sort` performance?  

**Reminder**: the file `records.csv` **MUST NOT be committed to GIT!**  

---

### Exercise 2 – Skip List  

### Required Language: C  

---

### Task  

Implement a data structure called `skip_list`.  
A `skip_list` is a type of linked list that stores an *ordered list* of elements.  

Unlike standard linked lists, a `skip_list` is a **probabilistic data structure** that supports search operations in **O(log n)** time. Insertions and deletions can also be performed in **O(log n)**. For this reason, skip lists are often used for indexing.  

Each node of a linked list contains a pointer to the next element. To find an element, you must traverse the list sequentially.  
A `skip_list` accelerates search by adding "express lanes" that allow skipping parts of the list. Each node contains not just one pointer but an **array of pointers** to further nodes.  

Example:  

![Example of a `skip_list`. From the node containing 6, you can directly jump to nodes 9 and 25 without visiting the others.](skiplist.png)  

---

### Suggested Data Structure  

```c
#define MAX_HEIGHT ....

typedef struct _SkipList SkipList;
typedef struct _Node Node;

struct _SkipList {
  Node *head;
  unsigned int max_level;
  int (*compare)(void*, void*);
};

struct _Node {
  Node **next;
  unsigned int size;
  void *item;
};
```

- MAX_HEIGHT: constant defining the maximum number of pointers per node
- unsigned int size: number of pointers in the current node
- unsigned int max_level: maximum level currently in use among all nodes

### Required Operations
- insertSkipList:
```
insertSkipList(list, I)

    new = createNode(I, randomLevel())
    if new->size > list->max_level
        list->max_level = new->size

    x = list->head
    for k = list->max_level downto 1 do
        if (x->next[k] == NULL || I < x->next[k]->item)
            if k < new->size {
              new->next[k] = x->next[k]
              x->next[k] = new
            }
        else
            x = x->next[k]
```
- randomLevel:
```
randomLevel()
    lvl = 1
    while random() < 0.5 and lvl < MAX_HEIGHT do
        lvl = lvl + 1
    return lvl
```

- searchSkipList:
```
searchSkipList(list, I)
    x = list->head
    for i = list->max_level downto 1 do
        while x->next[i]->item < I do
            x = x->next[i]

    x = x->next[1]
    if x->item == I then
        return x->item
    else
        return failure
```

#### Additional Requirements
Provide functions to create an empty skip_list and delete an existing one (freeing all allocated memory).
Implement unit tests for all operations according to the Unit Testing guidelines.

---

### Exercise 3 

### Required Language: Java

---

Task
Implement a library that provides a Min Heap data structure.
Requirements:
- Represent the heap internally using an array (other support structures may be used if necessary).
- Support an arbitrary number of elements (not known in advance).
- Be generic regarding the type of elements stored.
- Be generic regarding the comparison criterion between elements.
The library must support at least the following operations (with their required complexities, where n = number of elements):
- Create an empty min heap – O(1)
- Insert an element – O(log n)
- Return heap size – O(1)
- Return parent of an element – O(1)
- Return left child of an element – O(1)
- Return right child of an element – O(1)
- Extract the minimum element – O(log n)
- Decrease the value of an element – O(log n)

---
### Unit Testing
Implement unit tests for the library according to the Unit Testing guidelines.

---

### Exercise 4 

### Required Language: Java
---
Task
Implement a library that provides a Graph data structure optimized for sparse data.
The graph must support both directed and undirected graphs.
(Hint: an undirected graph can be implemented as a directed graph by inserting both (a, b) and (b, a) edges.)
The implementation must be generic with respect to node types and edge labels.
The library must support at least the following operations (with required complexities, where n = number of nodes or edges as appropriate):
- Create empty graph – O(1)
- Add node – O(1)
- Add edge – O(1)
- Check if graph is directed – O(1)
- Check if graph contains a node – O(1)
- Check if graph contains an edge – O(1) (*)
- Delete node – O(n)
- Delete edge – O(1) (*)
- Get number of nodes – O(1)
- Get number of edges – O(n)
- Retrieve nodes – O(n)
- Retrieve edges – O(n)
- Retrieve neighbors of a node – O(1) (*)
- Retrieve edge label between two nodes – O(1) (*)
(*) Assuming adjacency lists are used and graphs are sparse.

---

### Unit Testing
Implement unit tests for the library according to the Unit Testing guidelines.

---

#### Using the Graph and Heap Libraries
Implement Dijkstra’s algorithm for single-source shortest paths in a directed weighted graph (with non-negative edge weights).
The implementation must:
- Work with the custom Graph library (Exercise 4).
- Use the custom Min Heap library (Exercise 3) as a priority queue.

---

#### Application with Dijkstra
Write an application that uses the implemented Dijkstra algorithm to compute shortest paths from Torino on the graph described in:
https://datacloud.di.unito.it/index.php/s/PirTJpq4JMnpH3G
The file italian_dist_graph.csv contains distances (in meters) between Italian cities and some of their nearest neighbors.
CSV format:
- Fields separated by commas
- Records separated by \n
Each record contains:
- city1: (string) source city name (can contain spaces, no commas)
- city2: (string) destination city name (can contain spaces, no commas)
- distance: (float) distance in meters between the two cities
Notes:
- Treat the file as containing undirected edges (insert both directions).
- The dataset contains inaccuracies and inconsistencies.

Reminder: the file italian_dist_graph.csv MUST NOT be committed to GIT!
---
#### Validation
A correct implementation of Dijkstra’s algorithm, run on italian_dist_graph.csv, should find a shortest path between Torino and Catania of approximately 1207.68 km.
