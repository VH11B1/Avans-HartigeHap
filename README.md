# README B1 #

## Links ##

**Github:** https://github.com/vh11b1/hhbuk  
**Jenkins:** http://145.48.6.147:8081/job/hhB1/  
**Sonar:** http://145.48.6.147:9000/dashboard/index/1661  
**Database:** http://145.48.6.147:3306/hhb1  

## Starten met lege hh database ##
Er is een populator toegevoegd aan de bestaande applicatie. Default staat het automatisch aanmaken van de database uit. De default database is de online versie. Om dit te veranderen kunnen de volgende stappen doorlopen worden:

* In **datasource-jpa-tx.xml** de property _\<property name="url" value="jdbc:mysql://145.48.6.147:3306/hhb1" />_ uitzetten
* In **datasource-jpa-tx.xml** de property _\<property name="url" value="jdbc:mysql://localhost:3306/hh"/>_ aanzetten
* In **datasource-jpa-tx.xml** de property _\<prop key="hibernate.hbm2ddl.auto">create\</prop>_ aanzetten
* In **RestaurantController** _restaurantPopulatorService.createRestaurantsWithInventory();_ aanzetten
* In **PlanningOverviewController** _planningPopulatorService.populate();_ aanzetten

## Inloggegevens ##

Om in te loggen in de applicatie kan gebruik gemaakt worden van de combinaties:

* erco / erco
* mark / mark

Van alle medewerkers die door de random populator zijn toegoevoegd is de combinatie:

* %username% / admin

## Toegevoegde Functionaliteiten ##

### Employees ###
Het beheren van employees is mogelijk: toevoegen, wijzigen, verwijderen.

Deze acties zijn te vinden op de pagina */employees*.

- **Views**
    - webapp/WEB-INF/views/employees/create.jspx
    - webapp/WEB-INF/views/employees/edit.jspx
    - webapp/WEB-INF/views/employees/index.jspx
    - webapp/WEB-INF/views/employees/show.jspx
- **controller**
    - web/controller/EmployeeController
- **service**
    - service/EmployeeService
    
    
    - service/impl/EmployeeServiceImpl
- **repository**
    - repository/EmployeeRepositoryCustom
    - repository/EmployeeRepositoryImpl
- **domain**
    - domain/planning/Employee
    - domain/planning/EmployeeRole

### Inloggen ###
Het inlogsysteem is aangepast ten opzichte van het origineel. Gebruikersnaam/wachtwoord staan in de database en worden via Spring Security verder afgehandeld. 

* De wachtwoorden zijn versleuteld.
* De employees hebben rollen
* controllers/pagina's zijn afgeschermd op basis van deze rollen

Om te kijken of medewerkers "fysiek" aanwezig zijn hebben we gekozen voor een controle op basis van het inloggedrag. Bij elke inlog wordt gekeken of de medewerker op het juiste moment voor de juiste rol heeft ingelogd op basis van de lijst met planningen. Is dit niet het geval dan krijgt de supervisor een mail.
Vanwege de design patterns eisen is er gekozen voor een observer pattern, vandaar dat de klassen IObserver en MailObserver niet op een Spring-logische locatie staan.

Noot: tijdens het inloggen kan vanwege het grote aantal gegenereerde planningen een grote hoeveelheid console output geschreven worden.

- **service**
    - service/NotificationService
    
    
    - service/impl/EmailServiceImpl
- **domain**
    - domain/planning/IObserver
    - domain/planning/MailObserver
    - domain/planning/NotificationSubject
- **web**
    - web/HHAplicationListener

### Planning ###

Noot: de planning is gevuld met automatisch gegenereerde planningen en medewerkers, hierdoor ontstaan soms lange lijsten vanwege het ontbreken van pagination op alle locaties, zie hiervoor het onderdeel **pagination**.

Planningen zijn gebaseerd op dummy data die gegenereerd in de database gezet wordt. Alleen het uitlezen en filteren is mogelijk.

Planningen worden gefilterd met behulp van een Criteria Pattern implementatie vanwege Design Patterns, dit is niet optimaal in real world applicaties, zie hiervoor eveneens het onderdeel **pagination**.
Zie voor meer informatie over de structuur van deze patterns het JavaDoc commentaar in domain/planning/PlanningOverview.

De mogelijke acties (uitlezen) zijn te vinden op de pagina's   

* */plannings*.
* */plannings/daily*.
* */plannings/weekly*.

Momenteel is slechts een deel van de mogelijkheden te zien in de web-interface. Zie voor een uitgebreider overzicht van de mogelijkheden met de gebruikte design patterns de console prints in PlanningOverviewConsolePrints.

Wat niet wordt getoond met de test data is dat wanneer een medewerker vervangen is omdat de oorspronkelijke werknemer niet op tijd heeft ingelogd dit getoond wordt in het planning overzicht en de betreffende regel een rode kleur zou krijgen.

- **Views**
    - webapp/WEB-INF/views/plannings/index.jspx
    - webapp/WEB-INF/views/plannings/pageable.jspx
- **controller**
    - web/controller/PlanningOverviewController
- **service**
    - service/PlanningOverviewService
    - service/PlanningPopulatorService
    
    
    - service/impl/PlanningOverviewServiceImpl
    - service/impl/PlanningPopulatorServiceImpl
- **repository**
    - repository/PlanningRepository
    - repository/PannedSlotsRepository
- **domain**
    - domain/criteria/Criteria
    - domain/criteria/CriteriaBuilder
    - domain/criteria/AndCriteria
    - domain/criteria/OrCriteria
    - domain/criteria/NotCriteria
    
    
    - domain/criteria/impl/InPlannedRoleCriteria
    - domain/criteria/impl/PlannedCriteria
    - domain/criteria/impl/PresentCriteria
    
    
    - domain/criteria/filters/Filter
    - domain/criteria/filters/FilterDecorator
    - domain/criteria/filters/PlannedDayPartFilter
    - domain/criteria/filters/PlannedEmployeeFilter
    - domain/criteria/filters/PlannedRoleFilter
    - domain/criteria/filters/PlannedStartBetweenDatesFilter
    - domain/criteria/filters/PlannedStartDateFilter
    - domain/criteria/filters/PlannedStartFromDateFilter
    - domain/criteria/filters/PlannedTodayFilter
    
    
    - domain/planning/Planning
    - domain/planning/PlanningOverview
    - domain/planning/TimeSlot
    - domain/planning/PlannedSlot
    - domain/planning/ActualSlot
    - domain/planning/AvailableSlot
    
    
    - domain/query/CriteriaCommand
    - domain/query/PlannedAndNotPresentCommand
    - domain/query/PlannedInWrongRoleCommand
    - domain/query/PresentCommand
    - domain/query/PresentOrNotPresentCommand
    
### Bootstrap ###

Op de oorspronkelijke pagina's en de nieuwe is met behulp van Bootstrap een nieuw uiterlijk toegepast.

### Niet functionele toevoegingen ###

**Pagination**  

Vanwege design patterns is gekozen voor onder andere een criteria pattern. De gedachte hierachter is dat een lijst met planningen gefilterd wordt op basis van allerlei criteria als rol of inlogtijd. Dit heeft vrij grote gevolgen voor pagination, aangezien een Pageable rechtstreeks aan de repository gegeven wordt. Zodoende zouden we bijvoorbeeld een tiental resultaten opvragen voor pagina X maar vanwege criteria pattern filtering zouden we met compleet afwijkende aantallen overblijven. Vandaar dat gekozen is om alleen de grote lijst met alle planningen te voorzien van pagination. In de overige gevallen zou óf het criteria pattern aangepast moeten worden of een eigen implementatie van Pagination toegepast moeten worden. Vanwege de tijdsdruk is dit geen onderdeel van de huidige implementatie.
De pagination is te volgen vanaf de controller (PlanningOverviewController) methode *listPlanningsPageable*.

**i18n**

Deze is verwerkt in de views van planning. (/plannings, /plannings/daily, /plannings/weekly)

**Theming**

Deze is beschikbaar op alle pagina's.

**Security**  

Deze is verwerkt in het inlogsysteem. 

**Input validatie**  

Deze is te vinden in het domeinobject Employee.

**GUI richness**  

Deze is verkregen door middel van Bootstrap.

**Ontwikkelstraat**  

Zie hiervoor de links bovenaan de readme.

**Versiecontrole**  

Idem.

## Unit Tests ##
Voor het succesvol laten slagen van de unit tests moet het automatisch aanmaken en vullen van de database **uit** staan.

Toegevoegde tests t.o.v. originele applicatie:

1. **domain.criteria**
    1. CriteriaBuilderTest  
1. **domain.criteria.filters**
    1. PlannedDayPartFilterTest
    1. PlannedEmployeeFilterTest
    1. PlannedRoleFilterTest
    1. PlannedStartBetweenDatesFilterTest
    1. PlannedStartDateFilterTest
    1. PlannedStartFromDateFilterTest
    1. PlannedTodayFilterTest
    1. PlanningUtil
1. **domain.criteria.planning**
    1. PlanningOverviewConsolePrints (geen echte unit test)
    1. PlanningOverviewTest
1. **service**
    1. EmployeeRepoTest
    1. PlannedSlotsRepoTest
1. **web.it**
    1. EmailServiceTest

## Integratie Test ##

Om deze test uit te kunnen voeren zijn een aantal stappen noodzakelijk.

* De applicatie moet reeds draaien
* In **web.it.BrowserUtils** moet de webdriver mogelijk aangepast worden, zie de voorbeelden in comments
* In **web.it.HomePageLoginIT** moet de static URL mogelijk aangepast worden naar de root van de applicatie 

## Test coverage ##

*Type: coverage% (# gecovered / # totaal)*   

**Totaal:**

- **Class:** 85% (68/80)  
- **Method:** 47% (214/452)  
- **Line:** 36% (601/1660)  

**Domain:**

- **Class:** 90% (48/53)  
- **Method:** 61% (176/284)  
- **Line:** 60% (472/776)  

**Repository:**

- **Class:** 50% (1/2)  
- **Method:** 33% (1/3)  
- **Line:** 75% (28/37)  

**Service:**

- **Class:** 100% (10/10)  
- **Method:** 30% (23/76)  
- **Line:** 16% (65/392)

**Web:**

- **Class:** 60% (9/15)  
- **Method:** 15% (14/89)  
- **Line:** 7% (36/455)

## Design Patterns ##

**Voor het verwerken van planningen is gebruik gemaakt van:**

 - Criteria pattern \> (Criteria, combining criteria in CriteriaBuilder, etc.)
 - Factory pattern \> (Criteria.Type enum)
 - Builder pattern \> (CriteriaBuilder)
 - Singleton pattern \> (CriteriaBuilder is singleton (which is bad in real world application))
 - Command pattern \> (CriteriaCommand, combining CriteriaBuilder actions)
 - Decorator pattern \> (Filter, FilterDecorator, etc.)  
 
 Zie voor een complete uitleg de JavaDoc in domain/planning/PlanningOverview.
 
**Voor het controleren van het inloggen van medewerkers is gebruik gemaakt van:**
 
 - Observer pattern \> (HHAplicationListener, IObserver, MailObserver, NotificationSubject)
 - Template Pattern \> (NotificationService, EmailServiceImpl)

## Highlights ##

* listener/observer voor "fysieke" inlog controle
* criteria/filter patterns werken goed, beetje complex geworden door alle andere patterns (met name command is overbodige stap)

## Lowlights ##

* aanmaken van tabellen in database via populater, toevoegen van twee basis employees (mark/erco) gaat op niet-Spring manier.
* pagination niet overal toe kunnen passen vanwege criteria pattern

## Diagrammen ##

Zie **hhB1_Proftaak.vpp**
  
Alleen onze eigen toevoegingen zijn weergegeven om overdaad te voorkomen. Al onze toevoegingen zijn beperkt tot employees, rol aanduiding is dus achterwege gelaten.

**Use case:** Employees, Planning  
**Class:** Domain, Services, Repository, Controller  
**Activity:** Nagivation

# ORIGINELE README #

Deze Hartige Hap webapplicatie is gemaakt met Spring MVC.

### What is this repository for? ###

De applicatie bevat de volgende niet-funtionele aspecten:

* architectuur (gelaagde architectuur met MVC in de presentatielaag)
* persistentie in de data access laag met behulp van het object-relation mapping tool JPA 
* transactionaliteit (methodes op de service interfaces)
* tiling (header, footer, menu, body)
* i18n (Engels en Nederlands)
* security (autorisatie en personalisatie)
* theming
* input validatie
* pagination (opgevraagde content wordt geretourneerd in pagina's)
* ajax
* RESTful web service
* enkele tests


Open eindjes:

* basic css en GUI (dit is geen hoofddoel, maar ik ben het met je eens als je vindt dat het er niet uit ziet!)
* voldoende tests


### How do I get set up? ###

Installatie van de applicatie:

* De applicatie maakt gebruik van een MySQL database met de naam "hh".
* De database moet aanwezig zijn om de applicatie te laten werken, maar tabellen hoeven niet handmatig te worden aangemaakt. Dat doet Hibernate voor je.
* Mocht je een eerdere versie van de Hartige Hap webapplicatie geïnstalleerd hebben gehad, dan de oude database volledig weggooien en een nieuwe, lege database creëren, aangezien de tabellenstructuur anders is. 
* In het bestand src/main/resources/datasource-jpa-tx.xml staat de configuratie om de database te benaderen, die je indien nodig kunt aanpassen, met name username, password en URL. 
* In het bestand datasource-jpa-tx.xml staat de property <prop key="hibernate.hbm2ddl.auto">create</prop>. Met deze property worden de tabellen telkens weggegooid en opnieuw gecreëerd, als de applicatie wordt gedeployed. Als je dit niet wilt, verwijder dan deze property uit het bestand.
* In het tips & tricks document staat hoe je een bestaande applicatie importeert in STS. STS is de Spring ontwikkelomgeving.
* Deze applicatie maakt gebruik van Lombok. Hiervoor moet STS worden aangepast, anders begrijpt STS de broncode niet en geeft foutieve foutmeldingen. Om dit te doen, (1) download lombok.jar van http://projectlombok.org/, (2) dubbelklik op lombok.jar, (3) bevestig dat STS aangepast mag worden door Lombok.
* In STS, Ga menu "Run"/"Run Configurations...". Ga naar tabblad "Arguments" en zet bij "VM Arguments" de opties "-Xmx1024m -Xss192k -XX:MaxPermSize=256m -Dinsight-max-frames=6000" (zonder de quotes) erbij. Met het vergroten van de heap size en perm heap size naar de gegeven waarden voorkom je de foutmelding "java.lang.ClassNotFoundException: org.springframework.web.context.ConfigurableWebEnvironment". Met het vergroten van insight-max-frames naar 6000 voorkom je mogelijk de foutmelding "Imbalanced frame stack! (exit() called too many times)". Echter deze foutmelding wijst meestal op een fout in het JPA-deel van de applicatie.
* Als je een langzame computer hebt, is 120 seconden soms niet genoeg voor het bouwen en deployen van de applicatie. Als je het dan nogmaals probeert, lukt het wel, maar toch irritant. Je kunt de timeout van 120 seconden groter zetten door: in STS, dubbelklik op "VMware vFabric tc Server ..." die je vindt in de "Servers" tab linksonder. Er komt dan een schermpje op waarbij er een "Timouts" tab is. Als je Start (in seconds) op 240 zet, ben je van het probleem af. Zo niet, dan wordt het echt tijd voor een nieuwe computer.

Gebruik van de applicatie:

* deploy de applicatie:
    * rechter muisklik op project, "run as", "run on server"
    * check de console log op excepties
    * STS start automatisch een web browser op binnen STS
    * andere browser? Gebruik: http://localhost:8080/hh/
* log in met gebruikersnaam "employee" en password "employee"
* klik op het restaurant naar keuze
    * subsysteem bestelling: kies een tafel naar keuze, kies je gerechten, doe een of meer bestellingen, vraag om afrekenen
    * subsysteem keuken: plan bestelling, meld bestelling gereed
    * subsysteem bediening: meld bestelling geserveerd, meld rekening betaald
    * klanten subsysteem: CRUD op klanten
* geïmplementeerde business rules:
    * een lege bestelling kan niet gedaan worden
    * een lege rekening kan niet afgerekend worden
    * een rekening met nog niet bestelde bestelling mag niet afgerekend worden
* autorisatie en personalisatie:
    * er is ook een gebruiker "customer" met password "customer". Deze gebruiker heeft minder rechten
    * als je in het geheel niet bent ingelogd, mag je wel alles zien, maar mag je geen acties ondernemen