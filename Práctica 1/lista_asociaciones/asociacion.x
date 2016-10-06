const TAMA = 128; 
typedef int ID;
typedef string Key<TAMA>;
typedef string Content<TAMA>;
typedef struct Elemento * ElementoPtr;
typedef struct Lista * ListaPtr;

enum State {
	VALIDO = 0,
	ERROR = 1,
	SUSTITUIDO = 2,
	NOENCONTRADO = 3
};

struct Elemento {
	ID id;
	Key key;
	Content content;
	ElementoPtr sig;
};

struct Lista {
	ID id;
	Key key;
	ListaPtr sig;
	ElementoPtr first;
};

union ResultElemento switch(State state) {
	case VALIDO: Content content;
	default: void;
};

union ResultLista switch(State state) {
	case VALIDO: ElementoPtr elemPtr;
	default: void;
};

struct datos_nueva_asociacion {
	ID id;
	Key key;
	Content content;
};

struct datos_asociacion {
	ID id;
	Key key;
};

program LISTAASOCIACIONES {
	version LISTA_ASOCIACIONES_VERSION {
		State ponerAsociacion(datos_nueva_asociacion) = 1;
		ResultElemento obtenerAsociacion(datos_asociacion) = 2;
		State borrarAsociacion(datos_asociacion) = 3;
		ResultLista enumerar(ID) = 4;
	} = 1;
} = 0x20000001;

