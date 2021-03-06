/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "dns.h"

#define DIRECTORY_SIZE 20

machine directory1 [DIRECTORY_SIZE] ;
machine directory2 [DIRECTORY_SIZE] ;

void directory_fill()						// Rellena el directorio la primera vez que se lanza una llamada al servidor
{
	directory1[0].name = "DNS";				// El propio DNS es el primer dispositivo del directorio
	directory1[0].net = "red2";
	directory1[0].ip = "2.0";
	
	for(long int i = 1; i < DIRECTORY_SIZE/2; i++){		// La mitad del DNS para dispositivos por defecto la mitad para definidos por el usuario
		directory1[i].name = strcat("maquina",(char*) i);
		directory1[i].net = "red2";
		directory1[i].ip = strcat("2.",(char*) i);
	}

	directory2[0].name = "DNS";				// El propio DNS es el primer dispositivo del directorio
	directory2[0].net = "red1";
	directory2[0].ip = "1.0";
	
	
	for(long int i = 1; i < DIRECTORY_SIZE/2; i++){		// La mitad del DNS para dispositivos por defecto la mitad para definidos por el usuario
		directory2[i].name = strcat("maquina",(char*) i);
		directory2[i].net = "red1";
		directory2[i].ip= strcat("1.",(char*) i);
	}
}

str_t *
dns1_1_svc(machine *maquina,  struct svc_req *rqstp)		// Pregunta por una ip específica en la red 2
{	
	static str_t  result;
	int i;

	#ifndef DIR_FILLED					// Se rellena sólo una vez
	#define DIR_FILLED
	directory_fill();
	#endif

	if(strcmp(maquina->net, "red2") == 0){			// Estamos en la red especificada
		for(i = 1; i < DIRECTORY_SIZE; i++ || directory1[i].name == NULL ){
			if ( strcmp(maquina->name, directory1[i].name) == 0 ){	// Encontrado el dispositivo que coincide con el que se buscaba
				result = directory1[i].ip;
			} 
		}	
	} else {						// No estamos en la red especificada, preguntamos al dns superior
		printf("Este dns solo conoce los equipos de la red2, preguntando al dns superior\n");	
		CLIENT *clnt;

	#ifndef	DEBUG
		clnt = clnt_create ("localhost", DNSPROG2, DNSVERS, "udp");	// En un caso realista se usaría la ip del dns superior pero al ser una simulación usamos localhost
		if (clnt == NULL) {
			clnt_pcreateerror ("localhost");
			exit (1);
		}
	#endif	
	
		result = (str_t) dns2_1_svc(maquina, (struct svc_req *) clnt);
		if ( result == (str_t) NULL) {
			clnt_perror (clnt, "call failed");
		}

	#ifndef	DEBUG
		clnt_destroy (clnt);
	#endif	

	}

	if(result == NULL){					// No se ha encontrado el dispotivo al final
		printf("No se ha encontrado el equipo especificado en el dns de la red2");	
		result = "Not found";
	}

	return &result;
}

str_t *
dns2_1_svc(machine *maquina,  struct svc_req *rqstp)		// Pregunta por una ip específica en la red 1
{	
	static str_t  result;
	int i;

	#ifndef DIR_FILLED					// Se rellena sólo una vez
	#define DIR_FILLED
	directory_fill();
	#endif

	if(strcmp(maquina->net, "red1") == 0){			// Estamos en la red especificada
		for(i = 1; i < DIRECTORY_SIZE; i++ || directory2[i].name == NULL ){	
			if ( strcmp(maquina->name, directory2[i].name) == 0 ){	// Encontrado el dispositivo que coincide con el que se buscaba
				result = directory2[i].ip;
			} 
		}
	} else {						// No estamos en la red especificada, siendo el DNS superior el sistema termina aquí
		printf("Este dns solo conoce los equipos de la red1\n");			
	}

	if(result == NULL){					// No se ha encontrado el dispotivo al final
		printf("No se ha encontrado el equipo especificado en el dns de la red1\n");	
		result = "Not found";
	}

	return &result;
}	

str_t *
dns1rename_1_svc(machine *maquina,  struct svc_req *rqstp)	// Renombra o inserta un dispositivo en el dns de la red 2
{
	static str_t  result;
	int i;

	#ifndef DIR_FILLED					// Se rellena sólo una vez
	#define DIR_FILLED
	directory_fill();
	#endif
		
	if(strcmp(maquina->net, "red2") == 0){			// Estamos en la red especificada
		for(i = 1; i < DIRECTORY_SIZE; i++){
			if ( strcmp(maquina->name, directory1[i].name) == 0 ){	// Encontrado el dispositivo que coincide con el que se buscaba
				directory1[i].ip = maquina->ip;
				result = "Ip de la maquina cambiada correctamente";
			} 
		}		
		if(result == NULL && i < DIRECTORY_SIZE ){	// Aunque estamos en la red el dispositivo no ha sido encontrado, por tanto lo creamos
			directory1[i] = *maquina;
			result = "Maquina insertada";
		} else if ( i >= DIRECTORY_SIZE ) {		/// Crearíamos el dispositivo pero no queda espacio en el directorio
			result = "No hay espacio en el directorio para insertar la maquina, reinicia aumentando el tamaño de directorio";
		}
	} else {						// No estamos en la red especificada, preguntamos al dns superior
		printf("Este dns solo controla los equipos de la red2, delegando al dns superior\n");	
		CLIENT *clnt;

	#ifndef	DEBUG
		clnt = clnt_create ("localhost", DNSPROG2, DNSVERS, "udp");	// En un caso realista se usaría la ip del dns superior pero al ser una simulación usamos localhost
		if (clnt == NULL) {
			clnt_pcreateerror ("localhost");
			exit (1);
		}
	#endif	
	
		result = (str_t) dns2rename_1_svc(maquina, (struct svc_req *) clnt);
		if ( result == (str_t) NULL) {
			clnt_perror (clnt, "call failed");
		}

	#ifndef	DEBUG
		clnt_destroy (clnt);
	#endif	

	}
	
	if(result == NULL){					// No se ha encontrado el dispotivo al final
		printf("No se ha encontrado el equipo especificado en el dns de la red2");	
		result = "Not found";
	}

	return &result;
}

str_t *
dns2rename_1_svc(machine *maquina,  struct svc_req *rqstp)	// Renombra o inserta un dispositivo en el dns de la red 1
{
	static str_t  result;
	int i;

	#ifndef DIR_FILLED					// Se rellena sólo una vez
	#define DIR_FILLED
	directory_fill();
	#endif

	if(strcmp(maquina->net, "red1") == 0){			// Estamos en la red especificada
		for(i = 1; i < DIRECTORY_SIZE; i++){
			if ( strcmp(maquina->name, directory2[i].name) == 0 ){	// Encontrado el dispositivo que coincide con el que se buscaba
				directory2[i].ip = maquina->ip;
				result = "Ip de la maquina cambiada correctamente";
			} 
		}	
		if(result == NULL && i < DIRECTORY_SIZE ){	// Aunque estamos en la red el dispositivo no ha sido encontrado, por tanto lo creamos
			directory2[i] = *maquina;
			result = "Maquina insertada";
		} else if ( i >= DIRECTORY_SIZE ) {		// Crearíamos el dispositivo pero no queda espacio en el directorio
			result = "No hay espacio en el directorio para insertar la maquina, reinicia aumentando el tamaño de directorio";
		}
	} else {						// No estamos en la red especificada, siendo el DNS superior el sistema termina aquí
		printf("Este dns solo conoce los equipos de la red1\n");			
	}

	if(result == NULL){					// No se ha encontrado el dispotivo al final
		printf("No se ha encontrado el equipo especificado en el dns de la red1\n");	
		result = "Not found";
	}

	return &result;
	
}

