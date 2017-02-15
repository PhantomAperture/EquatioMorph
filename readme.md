# EquatioMorph
Werkzeug zum erlernen von algebraischen Grundansätzen. Dazu werden hauptsächlich physikalische Formeln genutzt, die sowohl vom Nutzer, als auch vom Programm selbst durch Äquivalenzumformung ineinander überführt werden können. Die dabei genutzten Rechenregeln werden dokumentiert um den Rechenweg nachvollziehbar zu machen. Über eine Suchfunktion kann auf die Formeldatenbank zugegriffen werden. Zum suchen können Variablennamen und -symbole, sowie physikalische Namen und Standardeinheiten genutzt werden.
Die umgestellten Formeln werden mathematisch korrekt und ansprechend angezeigt und sind durch den Nutzer per Maus- und Tastatursteuerung modellierbar.

# Oberfläche
Über eine Suchleiste wird dem Nutzer zugriff auf die Datenbank gewährt. Passende Einträge werden in einer Liste angezeigt und dem Nutzer zur Auswahl bereitgestellt. Die Rechenschritte sollen in einer weiteren Liste protokolliert werden. Gerenderte Formeln werden in einem JPanel derivat dargestellt (siehe EquationPanel).
Das Design entspricht dem ästhetischen Standard und sollte ansprechend gewählt sein.
Meilensteine: Fenster, Design, Struktur der gerenderten Formeln, korrekte Anzeige dieser, Editierbarkeit durch den Nutzer, Auflisten von Rechenschritten 

# Nutzersteuerung
Der Nutzer interagiert über Maus und Tastatur mit dem Programm. Variablen werden mit Mausklick editiert und Werteingaben per Tastatur erzeugt.
Meilensteine: Eingabe wird an Komponente weitergeleitet

# Datenbank
Die Datenbank ist in der Sprache SQL verfasst und sollte Remote- und Localaccess bieten. Die Daten sollten aufgrund der lokalen Speicherung komprimiert sein. Die Datenbankschnittstelle ist von überall im Programm erreichbar. Sie beinhalten von den Entwicklern eingegebene Formeln in denen Variablen bei Bedarft eine physikalische Bedeutung sowie Einheit zugeordnet werden kann. Auch Naturkonstanten sollen in der Datenbank abgespeichert sein. Schlussendlich kann auch der Nutzer eigene Formeln abspeichern, diese sind dann jedoch nur lokal erhältlich.
Meilensteine: Struktur, Aktualisierungsprozess, Anfragen, Serialisierung/Deserialisieung, Inhalt 

# Äquivalenzumformung
Algorithmischen Umstellen der Formel nach einer Variable. Die Entwicklung dieses Algorithmus ist teil des Projekts.
Meilensteine: Umstellen von Grundrechenarten, Umstellen von erweiterten Rechenarten, Optimierung der Geschwindigkeit, Auflisten von Rechenschritten
