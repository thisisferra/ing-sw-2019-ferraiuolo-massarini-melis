Progetto finale del corso di Ingegneria del Software 2018/2019

Componenti del gruppo n° 24:
Alessandro Marco Ferraiuolo - 828596 - thisisferra
Mattia Massarini - 825664 - mattiamassarini
Marco Melis - 829686 - merklind

Funzionalità Implementate: 
Regole Complete
GUI
RMI

Funzionalità Aggiuntiva: 
Persistenza

Tecnologie utilizzate 
Java 8
JavaFX, parte di Java8
Libreria Gson per parsing e serializzazione di file json di armi, power ups, munizioni, mappe.
Libreria SimpleJSON per l'implementazione della persistenza.
Sonarqube
Junit

Il progetto è stato sviluppato interamente su sistemi UNIX.

Istruzioni per avvio da terminale

Linux, Mac OS e windows:  ~/ java -jar adrenalina.jar
L'eventuale file di salvataggio della partita dovrà essere situato allo stesso livello del file jar per poter essere letto da quest'ultimo

Dopo l'avvio si dovrà specificare se eseguire il server (1) o il client (2).
Nel primo caso verrà inoltre richiesto all'utente di specificare la durata del timer del turno, da 50 a 300 secondi.
Il server caricherà automaticamente una partita già iniziata se il file AdrenalineMatchData.json è presente, altrimenti
ne verrà iniziata una nuova.
Può esistere una sola partita salvata alla volta e possono effettuare il login esclusivamente i giocatori precedenti.
Nel caso in cui non si volesse proseguire la partita precedente il file AdrenalineMatchData.json dovrà essere cancellato.

Generazione jar 
Il file jar è stato generato utilizzando il plugin di Intellij IDEA.
File -> Project Structure -> Artifacts -> Add... (+) -> JAR -> from module with dependencies... 
Come Main Class inserire la App Class all'interno del modulo adrenalina, come directory per META-IN/MANIFEST.MF inserire il path che porta alla cartella Resources.
In Project Structure selezionare l'artifact appena creato e importare tutte le dependencies mostrate nella scheda Output Layout.
Selezionare quindi Type: JAR e la cartella dove salvare il file JAR.
Dal menù principale di Intellij -> Build -> Build Artifacts... -> selezionare il file jar -> Build.

