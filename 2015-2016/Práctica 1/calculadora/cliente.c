/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "calculadora.h"


void
calculadora_1(char *host)
{
	CLIENT *clnt;
	union{
		datos sumar_1_arg;
		datos restar_1_arg;
		datos multiplicar_1_arg;
		datos dividir_1_arg;
	}argumento;
	int opcion;
	int *result_1;
	xdrproc_t _xdr_argument, _xdr_result_1;
	char *(*local)(char *, struct svc_req *);

	printf("CALCULADORA\n===========\n\nOpciones:\n1.- Sumar\n2.- Restar\n3.- Multiplicar\n4.- Dividir\nSelección: ");
	scanf("%d",&opcion);
	while(opcion<1 || opcion>4) {
		printf("Error: selección no válida. Selecciona una nueva opción.\n");
		printf("\n\nOpciones:\n1.- Sumar\n2.- Restar\n3.- Multiplicar\n4.- Dividir\nSelección: ");
		scanf("%d",&opcion);
	}

	switch (opcion) {

		case 1:		
			printf("\nPrimer argumento: ");
			scanf("%d",&argumento.sumar_1_arg.dato1);

			printf("\nSegundo argumento: ");
			scanf("%d",&argumento.sumar_1_arg.dato2);

			break;

		case 2:		
			printf("\nPrimer argumento: ");
			scanf("%d",&argumento.restar_1_arg.dato1);

			printf("\nSegundo argumento: ");
			scanf("%d",&argumento.restar_1_arg.dato2);

			break;

		case 3:		
			printf("\nPrimer argumento: ");
			scanf("%d",&argumento.multiplicar_1_arg.dato1);

			printf("\nSegundo argumento: ");
			scanf("%d",&argumento.multiplicar_1_arg.dato2);

			break;

		case 4:		
			printf("\nPrimer argumento: ");
			scanf("%d",&argumento.dividir_1_arg.dato1);

			printf("\nSegundo argumento: ");
			scanf("%d",&argumento.dividir_1_arg.dato2);

			while(argumento.dividir_1_arg.dato2 == 0){
				printf("\nMath Error: No es posible dividir entre 0. Introduce otro número: ");				
				scanf("%d",&argumento.dividir_1_arg.dato2);
			}

			break;
		
		default: 
			break;
	}

#ifndef	DEBUG
	clnt = clnt_create (host, CALCULADORA, CALCULADORA_VERSION , "udp");
	if (clnt == NULL) {
		clnt_pcreateerror (host);
		exit (1);
	}
#endif	/* DEBUG */
	
	switch (opcion) {

		case 1:	
			result_1 = sumar_1(&argumento.sumar_1_arg, clnt);
			break;

		case 2:
			result_1 = restar_1(&argumento.restar_1_arg, clnt);
			break;
		case 3: 
			result_1 = multiplicar_1(&argumento.multiplicar_1_arg, clnt);
			break;
		case 4: 
			result_1 = dividir_1(&argumento.dividir_1_arg, clnt);
			break;
		default:
			result_1 = (int *) NULL;
			break;
	}
			
	if (result_1 == (int *) NULL) {
		clnt_perror (clnt, "call failed");
	}

	printf("Resultado: %d\n",*result_1);

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
	calculadora_1 (host);
	exit (0);
}
