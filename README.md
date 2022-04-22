# MusicFestivalMPP
:ticket: :guitar: :ferris_wheel: :notes:
### Cerinta problemei 2:
*Organizatorii unui festival de muzica au mai multe oficii în țară unde se vând bilete la festival. La fiecare oficiu 
se folosește un sistem soft pentru a vinde bilete. Persoana de la fiecare oficiu folosește o aplicație desktop cu 
următoarele funcționalități:*
1. ***Login.*** După autentificarea cu succes, o nouă fereastră se deschide în care sunt afișați toți artiștii 
(numele, data și locul spectacolului, numărul de locuri disponibile și numărul de locuri deja vândute). 
Un artist poate susține mai multe spectacole. 
2. ***Căutare.*** După autentificarea cu succes, persoana de la oficiu poate căuta artiștii care au spectacol într-o 
anumită zi. Aplicația va afișa în altă listă/alt tabel toți artiștii care au spectacol în acea zi, locația, ora 
începerii și numărul de locuri disponibile. 
3. ***Cumpărare.*** Angajatul poate vinde bilete pentru un anumit spectacol. Pentru vânzare se introduce 
numele cumpărătorului și numărul de locuri dorite. După vânzare toți angajații de la toate oficiile văd 
lista actualizată a spectacolelor. Dacă la un spectacol nu mai sunt locuri disponibile, spectacolul va fi 
afișat cu culoare roșie. 
4. ***Logout.*** <br><br>
### Structura repository-ului: :computer:
In acest repository exista 6 branch-uri: 
1. main
2. ***lab01-java***, implementarea serverului si a clientului in Java, realizate fara ajutorul altor tehnologii;
3. ***lab01-c#***, implementarea serverului si a clientului in C#, realizate fara ajutorul altor tehnologii;
4. ***java-grpc***, implementarea serverului in Java folosind gRpc;
5. ***c#-grpc***, implementarea clientului in C# folosind gRpc;
6. ***orm-java***, implementarea serverului in Java folosing gRpc si Hibernate pentru stocarea entitatilor Performance si Ticket in baza de daate;

*Lab01: Cele doua implementari contin modelul, interfetele pentru repository si teste.* <br>
*Lab02: Au fost impementate clasele repository, s-a stabilit conexiunea la baza de date si am folosit log4net.* <br>
*Lab03: Au fost implementate service-urile si validatorii, alaturi de interfata grafica pentru limbajul Java.* <br>
*Lab04: Au fost implementate service-urile si validatorii, alaturi de interfata grafica pentru limbajul C#.* <br>
*Lab05: A fost implementata partea de networking pentru limbajul Java.* <br>
*Lab06: A fost implementata partea de networking pentru limbajul C#.* <br>
*Lab07: A fost implementat serverul in Java si clientul in C#, folosing gRpc.* <br>
*Lab08: A fost realizata stocarea claselor Performance si Ticket in baza de date folosind Hibernate.* <br>
