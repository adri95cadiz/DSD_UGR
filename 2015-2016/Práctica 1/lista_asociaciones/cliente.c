/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "asociacion.h"

#define TAMA 128

void
resultado_insercion(State state) {
	if(state==VALIDO) {
		printf("La entrada se ha insertado de forma correcta.\n\n");
	} else {
		printf("Error en la inserción.\n\n");
	}
}

void
resultado_consulta(ResultElemento result) {
	if(result.state==VALIDO) {
		printf("Contenido: %s\n\n",result.ResultElemento_u.content);
	} else {
		printf("Error: No existe ningún elemento con esa clave en esa lista.\n\n");
	}
}

void
resultado_enum(ResultLista result) {
	if(result.state==VALIDO) {
		ElementoPtr elemPtr = result.ResultLista_u.elemPtr;
		printf("%s: %s\n", elemPtr->key, elemPtr->content);
		while(elemPtr->sig!=NULL) {
			elemPtr = elemPtr->sig;
			printf("%s: %s\n", elemPtr->key, elemPtr->content);
		}
		printf("\n");
	} else {
		printf("La lista indicada no existe en el sistema.\n\n");
	}
}

void
resultado_borrar(State state) {
	if(state==VALIDO) {
		printf("Elemento eliminado correctamente\n\n");
	} else {
		printf("Error: No existe ningún elemento con esa clave en esa lista.\n\n");
	}
}

void
listaasociaciones_1(char *host)
{
	CLIENT *clnt;
	State  *result_1;
	ResultElemento  *result_2;
	State  *result_3;
	ResultLista  *result_4;
	union {
		datos_nueva_asociacion ponerasociacion_1_arg;
		datos_asociacion obtenerasociacion_1_arg;
		datos_asociacion borrarasociacion_1_arg;
		ID enumerar_1_arg;
	} argument;

#ifndef	DEBUG
	clnt = clnt_create (host, LISTAASOCIACIONES, LISTA_ASOCIACIONES_VERSION, "udp");
	if (clnt == NULL) {
		clnt_pcreateerror (host);
		exit (1);
	}
#endif	/* DEBUG */

	int opcion;
	int inInt;
	char inStr [TAMA];

	printf("LISTAS_ASOCIACIONES\n=====================\n\nOpciones:\n1.-Insertar elemento en lista.\n2.-Obtener elemento de lista.\n3.-Eliminar elemento de lista.\n4.-Enumerar elementos de lista.\n5.-Salir\nSelección: ");
	scanf("%d",&opcion);
	while(opcion!=5) {
		switch(opcion) {
			case 1:
				printf("\nHa seleccionado insertar elemento en lista.\n");
				printf("Introduzca ID: ");
				scanf("%d",&inInt);
				argument.ponerasociacion_1_arg.id = inInt;
				printf("Introduzca Clave: ");
				scanf("%s",inStr);
				argument.ponerasociacion_1_arg.key = strdup(inStr);
				printf("Introduzca Contenido: ");
				scanf("%s",inStr);
				argument.ponerasociacion_1_arg.content = strdup(inStr);
				result_1 = ponerasociacion_1(&argument.ponerasociacion_1_arg, clnt);
				resultado_insercion(*result_1);
				xdr_free((xdrproc_t)xdr_State,(char*)result_1);
				if (result_1 == (State *) NULL) {
					clnt_perror (clnt, "call failed");
				}
				break;
			case 2:
				printf("\nHa seleccionado obtener elemento de lista.\n");
				printf("Introduzca ID: ");
				scanf("%d",&inInt);
				argument.obtenerasociacion_1_arg.id = inInt;
				printf("Introduzca Clave: ");
				scanf("%s",inStr);
				argument.obtenerasociacion_1_arg.key = inStr;
				result_2 = obtenerasociacion_1(&argument.obtenerasociacion_1_arg, clnt);
				resultado_consulta(*result_2);
				xdr_free((xdrproc_t)xdr_ResultElemento,(char*)result_2);
				if (result_2 == (ResultElemento *) NULL) {
					clnt_perror (clnt, "call failed");
				}
				break;
			case 3:
				printf("\nHa seleccionado eliminar elemento de lista.\n");
				printf("Introduzca ID: ");
				scanf("%d",&inInt);
				argument.borrarasociacion_1_arg.id = inInt;
				printf("Introduzca Clave: ");
				scanf("%s",inStr);
				argument.borrarasociacion_1_arg.key = inStr;
				result_3 = borrarasociacion_1(&argument.borrarasociacion_1_arg, clnt);
				resultado_borrar(*result_3);
				xdr_free((xdrproc_t)xdr_State,(char*)result_3);
				if (result_3 == (State *) NULL) {
					clnt_perror (clnt, "call failed");
				}
				break;
			case 4:
				printf("\nHa seleccionado enumerar elementos de lista.\n");
				printf("Introduzca ID: ");
				scanf("%d",&inInt);
				argument.enumerar_1_arg = inInt;
				result_4 = enumerar_1(&argument.enumerar_1_arg, clnt);
				resultado_enum(*result_4);
				xdr_free((xdrproc_t)xdr_ResultLista,(char*)result_4);
				if (result_4 == (ResultLista *) NULL) {
					clnt_perror (clnt, "call failed");
				}
				break;
			default:
				printf("Opción incorrecta\n");
				break;
		}
		printf("\nOpciones:\n1.-Insertar elemento en lista.\n2.-Obtener elemento de lista.\n3.-Eliminar elemento de lista.\n4.-Enumerar elementos de lista.\n5.-Salir\nSelección: ");
		scanf("%d",&opcion);
	}

#ifndef	DEBUG
	clnt_destroy (clnt);
#endif	 /* DEBUG */
}


int
main (int argc, char *argv[])
{
	char *host;

	if (argc < 2) {
		printf ("usage: %s server_host\n", argv[0]);
		exit (1);
	}
	host = argv[1];
	listaasociaciones_1 (host);
exit (0);
}
