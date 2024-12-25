PROJECTE INDIVIDUAL 2DAM
Ivan Simon Meroño

Directori AppDesktop

Aqui pot veure el directori on es troba l'executable de la app d'escriptori. És necessari que tinguis el servei de Xampp corrent ja que des d'ahi carrega els productes i categories.

El fluix de l'aplicacio es el seguent:

- En executar el .exe s'obrira el dashboard principal. Allà podrás veure una barra lateral situada a l'esquerra amb el logo de l'aplicacio, i tres botons (Dashboard, Products, Categories). D'altra banda,
la gran part de la finestra és el dashboard. Pots veure dues cards que mostren el total de productes i categories respectivament (en cas de no tenir el Xampp corrent mostrara per defecte 0).

- En cas de clicar el botó Products del sidebar seras redirigit a una finestra on es mostraran tots els productes en una llista, si no tens el servei corrent no es mostrara cap producte.

- De la mateixa manera, si fas clic a Categories redirigirà a la finestra on es mostraran totes les categories en llista. En cas de no tenir Xampp executant-se no es mostrara cap.

- Tornant al dahboard, cada card mostra un titol, el numero de productes / categories respectivament i un botó.
    - En cas de clicar el "View Products" se't redirigirà a una finestra on es mostraran tots els productes en llista i dos botons que serveixen per editar o eliminar el producte. A més, podras veure un botó
    per crear un nou producte. Tant el "Create new product" com el "Edit" obriran un modal a mode de formulari per introduir les dades. 
    En cas del CREATE mostrarà els camps buits i hauras d'omplir obligatoriament el nom de producte, preu, ruta de la imatge, i categoria (sera una llista que es desplega amb les opcions de categories que trobi 
    a la bdd). 
    En el cas del EDIT, els camps estaran omplerts amb les dades que trobi en l'ID del producte, i podràs canviar-les i reflectir-les a la bdd. 
    Si el que vols es eliminar-lo se't mostrara una alerta de si estas segur d'eliminar el producte, i si prems el boto OK l'eliminarà.

    - En cas de clicar el "View Categories" se't redirigirà a una finestra on es mostraran totes les categories en llista i dos botons que serveixen per editar o eliminar la categoria. A més, podras veure un botó
    per crear una nova categoria. Tant el "Create new category" com el "Edit" obriran un modal a mode de formulari per introduir les dades. 
    En cas del CREATE mostrarà els camps buits i hauras d'omplir obligatoriament el nom de categoria i descripcio. 
    En el cas del EDIT, els camps estaran omplerts amb les dades que trobi en l'ID de la categoria, i podràs canviar-les i reflectir-les a la bdd. 
    Si el que vols es eliminar-la se't mostrara una alerta de si estas segur d'eliminar la categoria, i si prems el boto OK l'eliminarà.