/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "calculadora.h"

int *
sumar_1_svc(datos* data,  struct svc_req *rqstp)
{
	static int result;
	datos argumento = *data;
	result = argumento.dato1 + argumento.dato2;
	return &result;
}

int *
restar_1_svc(datos* data,  struct svc_req *rqstp)
{
	static int result;
	datos argumento = *data;
	result = argumento.dato1 - argumento.dato2;
	return &result;
}

int *
multiplicar_1_svc(datos* data,  struct svc_req *rqstp)
{
	static int result;
	datos argumento = *data;
	result = argumento.dato1 * argumento.dato2;
	return &result;
}

int *
dividir_1_svc(datos* data,  struct svc_req *rqstp)
{
	static int result;
	datos argumento = *data;
	result = argumento.dato1 / argumento.dato2;
	return &result;
}
