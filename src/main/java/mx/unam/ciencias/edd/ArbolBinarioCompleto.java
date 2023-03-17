package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.</p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Cola para recorrer los vértices en BFS. */
        private Cola<Vertice> cola;

        /* Inicializa al iterador. */
        private Iterador() {
            // Aquí va su código.
            cola.mete(raiz);
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return cola.esVacia();
        }

        /* Regresa el siguiente elemento en orden BFS. */
        @Override public T next() {
            // Aquí va su código.
            if (hasNext()) {
                Vertice temp = cola.saca();
                
                if (temp.izquierdo != null)
                    cola.mete(temp.izquierdo);
                
                if (temp.derecho != null)
                    cola.mete(temp.derecho);
                
                return temp.elemento;
            }
            
            return null;
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() { super(); }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        if (elemento == null)
            throw new IllegalArgumentException();
        
        if (esVacia()) {
            Vertice v = nuevoVertice(elemento);
            raiz = v;
            elementos = 1;
        }

        Cola<Vertice> cola = new Cola<Vertice>();
        cola.mete(raiz);

        while (!cola.esVacia()) {
            Vertice n = cola.saca();

            if (n.izquierdo == null) {
                Vertice temp = nuevoVertice(elemento);
                n.izquierdo = temp;
                temp.padre = n;
                return;
            }

            if (n.derecho == null) {
                Vertice temp = nuevoVertice(elemento);
                n.derecho = temp;
                temp.padre = n;
                return;
            }

            cola.mete(n.izquierdo);
            cola.mete(n.derecho);
        }
    }
    

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        if(esVacia() || elemento == null)
            return;

        Cola<Vertice> cola = new Cola<>();
        cola.mete(raiz);

        Vertice ultimo = null;

        while (!cola.esVacia()){
            Vertice temp = cola.saca();
            ultimo = temp;

            if (temp.izquierdo != null)
                cola.mete(temp.izquierdo);

            if (temp.derecho != null)
                cola.mete(temp.derecho);
        }

        while (!cola.esVacia()) {
            Vertice n = cola.saca();

            if (n.elemento.equals(elemento)) {

            }
        }

    }

    /**
     * Regresa la altura del árbol. La altura de un árbol binario completo
     * siempre es ⌊log<sub>2</sub><em>n</em>⌋.
     * @return la altura del árbol.
     */
    @Override public int altura() {
        // Aquí va su código.
        return log(elementos);
    }
    
    private int log(int n) {
        int r = 0;
        while(n > 1) {
            n /= 2;
            r++;
        }
        
        return r;
    }

    /**
     * Realiza un recorrido BFS en el árbol, ejecutando la acción recibida en
     * cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void bfs(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        Cola<Vertice> cola = new Cola<Vertice>();
        cola.mete(raiz);

        while (!cola.esVacia()) {
            Vertice n = cola.saca();
            accion.actua(n);

            if (n.izquierdo != null)
                cola.mete(n.izquierdo);

            if (n.derecho != null)
                cola.mete(n.derecho);
        }
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
