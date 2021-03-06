/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "dns.h"

#define DIRECTORY_SIZE 10

str_t *
dns1_1_svc(machine *maquina,  struct svc_req *rqstp)
{	
	machine directory [DIRECTORY_SIZE] ;
	static str_t  result;

	directory[0].name = "DNS";
	directory[0].net = "red1";
	directory[0].ip = "1.0";
	
	for(long int i = 1; i < DIRECTORY_SIZE; i++){
		strcpy(directory[i].name, strcat("maquina",(char*) i));
		strcpy(directory[i].net, "red2");
		strcpy(directory[i].ip, strcat("2.",(char*) i));
	}
	

	if(strcmp(maquina->net, "red2") == 0){
		for(int i = 1; i < DIRECTORY_SIZE; i++){
			if ( strcmp(maquina->name, directory[i].name) == 0 ){
				result = directory[i].ip;
			} 
		}
	} else {
		printf("Este dns solo conoce los equipos de la red2, preguntando al dns superior\n");	
		CLIENT *clnt;

	#ifndef	DEBUG
		clnt = clnt_create ("localhost", DNSPROG2, DNSVERS, "udp");
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

	if(result == NULL){
		printf("No se ha encontrado el equipo especificado en ningun dns");	
		result = "Not found";
	}

	return &result;
}

str_t *
dns2_1_svc(machine *maquina,  struct svc_req *rqstp)
{	
	machine directory [DIRECTORY_SIZE] ;
	static str_t  result;

	directory[0].name = "DNS";
	directory[0].net = "red1";
	directory[0].ip = "1.0";
	
	for(long int i = 1; i < DIRECTORY_SIZE; i++){
		strcpy(directory[i].name, strcat("maquina",(char*) i));
		strcpy(directory[i].net, "red1");
		strcpy(directory[i].ip, strcat("1.",(char*) i));
	}
	

	if(strcmp(maquina->net, "red1") == 0){
		for(int i = 1; i < DIRECTORY_SIZE; i++){
			if ( strcmp(maquina->name, directory[i].name) == 0 ){
				result = directory[i].ip;
			} 
		}
	} else {
		printf("Este dns solo conoce los equipos de la red1\n");			
	}

	if(result == NULL){
		printf("No se ha encontrado el equipo especificado en este dns\n");	
		result = "Not found";
	}

	return &result;
}
