document.addEventListener('DOMContentLoaded', () => {
  const views = document.querySelectorAll('.view');
  const navLinks = document.querySelectorAll('.nav-link');

  function showView(viewId) {
    views.forEach(view => view.classList.add('hidden'));
    document.getElementById(viewId + '-view').classList.remove('hidden');
  }

  navLinks.forEach(link => {
    link.addEventListener('click', () => {
      navLinks.forEach(l => l.classList.remove('active'));
      link.classList.add('active');
      showView(link.dataset.view);
    });
  });

  // Fetch and display product and category data
  fetch('http://127.0.0.1/proyecto_ism/consultaproductos.php')
    .then(response => response.text())
    .then(xmlString => {
      const parser = new DOMParser();
      const xmlDoc = parser.parseFromString(xmlString, 'application/xml');
      const productos = Array.from(xmlDoc.getElementsByTagName('producto'));

      document.getElementById('product-count').textContent = `Total Products: ${productos.length}`;
      const productList = document.getElementById('product-list');
      productList.innerHTML = ''; // Limpiar lista existente

      productos.forEach(producto => {
        const nombreProducto = producto.getElementsByTagName('nombre_producto')[0].textContent;
        const precio = producto.getElementsByTagName('precio')[0].textContent;
        const nombre_categoria = producto.getElementsByTagName('nombre_categoria')[0].textContent;
        const li = document.createElement('li');
        li.textContent = nombreProducto;
        productList.appendChild(li);
      });
    })
    .catch(error => console.error('Error al cargar productos:', error));

  fetch('http://127.0.0.1/proyecto_ism/consultacategorias.php')
    .then(response => response.text())
    .then(xmlString => {
      const parser = new DOMParser();
      const xmlDoc = parser.parseFromString(xmlString, 'application/xml');
      const categorias = Array.from(xmlDoc.getElementsByTagName('categoria'));

      document.getElementById('category-count').textContent = `Total Categories: ${categorias.length}`;
      const categoryList = document.getElementById('category-list');
      categoryList.innerHTML = ''; // Limpiar lista existente

      categorias.forEach(categoria => {
        const nombreCategoria = categoria.getElementsByTagName('nombre_categoria')[0].textContent;
        const li = document.createElement('li');
        li.textContent = nombreCategoria;
        categoryList.appendChild(li);
      });
    })
    .catch(error => console.error('Error al cargar categorías:', error));
});

function navigateTo(view) {
  location.href = view + '.html';
}
