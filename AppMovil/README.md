PROJECTE INDIVIDUAL 2DAM
Ivan Simon Meroño

Directori App Movil

Aqui podràs trobar tant el directori main de l'aplicació com l'arxiu .apk.

Les funcionalitats que té l'aplicacio son:

- Pantalla de carrega amb el logo de l'aplicacio i una progress-bar amb un creixement indeterminat que durarà uns 4 segons. En passar els 4 segons pots ser redirigit a diferents pantalles.

- En cas de no tenir cap sessió iniciada seràs redirigit a una finestra on hauràs d'escollir entre registrar-te o iniciar sessió. 
    - En cas de clicar el registrar-se hauràs d'omplir els camps email, contrasenya, confirmar contrasenya (ha de coincidir amb l'altre), direccio i telefon. Un cop prems el botó de registrar-te, si 
    les dades han estat correctes s'enviarà un correu de validació a l'email que has introduit. No podras iniciar sessió fins que verifiquis el correu.

    - En cas de clicar iniciar sessió hauras d'omplir els camps email i contrasenya. En cas de que els camps no siguin correctes mostrara una alerta, i, si son correctes iniciarà sessió i seràs redirigit
    a la pantalla principal de l'aplicacio. A més, en la pantalla de login pots iniciar sessió amb google, o fer clic al botó de "He oblidat la contrasenya", el qual enviarà un missatge al correu introduit
    per fer un reset de la contrasenya. Aixó ha estat possible gràcies a Firebase.

- D'altra banda, en cas de tenir la sessió iniciada seràs redirigit al menu principal de l'aplicació. Aqui podras observar un botó que simula el teu perfil. També podras veure una imatge i a sota els productes
que tinguin algun descompte. A la part inferior de l'aplicació podras veure dos botons més, el de la llista de productes, i la cistella de compra.
    - Si fas clic al botó de perfil podras veure una imatge per defecte i l'email amb el qual s'ha iniciat sessió. Podras observar també un botó amb el que es tanca sessió i ets redirigit de nou a la pantalla
    on has d'escollir entre registrar-se o iniciar sessió i continuar amb el fluix. La imatge de perfil pot ser canviada per una que agafis del dispositiu.

    - En fer clic al botó de la llista de productes podràs veure tots els productes en una llista de dues columnes. Cada producte té una imatge, nom, categoria i preu. Pots clicar a un producte, i es mostrarà
    en detall. El detall permet veure en gran el producte amb el nom, imatge, preu, categoria i valoracions que trobi d'altres clients. Les valoracions tenen un rating de 1-5 estrelles, i mostren l'email de 
    l'usuari que ha registrat la valoració, el numero d'estrelles que l'ha donat al producte, una descripcio de la seva opinio i la data en la que es va fer la critica. També podras observar un botó a sota de la 
    informacio del producte pero a sobre de les valoracions el qual afegeix el producte al cistell. En cas de tenir el producte al cistell ja, mostrara un missatge de que ja tens el producte.

    - En fer clic al botó de la cistella podràs veure aquells productes que hagis afegit en una llista d'una unica columna. Cada producte té una imatge, nom, categoria, preu, contador amb la quantitat del producte,
    i dos botons per afegir o treure una unitat. Si tens una unitat del producte i la treus serà eliminat del cistell automaticament. A la part inferior podras veure un botó que redirigirà a una mena de ticket amb
    les dades de la teva compra. Es mostrara una llista amb el nom del producte, la quantitat escollida, el preu per unitat i preu total del producte (si val 2 euros i tinc 3 unitats el preu total producte serà 6). 
    A sota del tot hi ha una etiqueta amb el preu total del cistell, la qual s'actualitza a mesura que s'afegeixen o eliminen productes del cistell. Fa la suma total dels preus totals per producte.