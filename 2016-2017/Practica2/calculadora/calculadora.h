/*
 * Please do not edit this file.
 * It was generated using rpcgen.
 */

#ifndef _CALCULADORA_H_RPCGEN
#define _CALCULADORA_H_RPCGEN

#include <rpc/rpc.h>


#ifdef __cplusplus
extern "C" {
#endif


struct datos {
	int dato1;
	int dato2;
};
typedef struct datos datos;

#define CALCULADORA 0x20000001
#define CALCULADORA_VERSION 1

#if defined(__STDC__) || defined(__cplusplus)
#define sumar 1
extern  int * sumar_1(datos , CLIENT *);
extern  int * sumar_1_svc(datos , struct svc_req *);
#define restar 2
extern  int * restar_1(datos , CLIENT *);
extern  int * restar_1_svc(datos , struct svc_req *);
#define multiplicar 3
extern  int * multiplicar_1(datos , CLIENT *);
extern  int * multiplicar_1_svc(datos , struct svc_req *);
#define dividir 4
extern  int * dividir_1(datos , CLIENT *);
extern  int * dividir_1_svc(datos , struct svc_req *);
extern int calculadora_1_freeresult (SVCXPRT *, xdrproc_t, caddr_t);

#else /* K&R C */
#define sumar 1
extern  int * sumar_1();
extern  int * sumar_1_svc();
#define restar 2
extern  int * restar_1();
extern  int * restar_1_svc();
#define multiplicar 3
extern  int * multiplicar_1();
extern  int * multiplicar_1_svc();
#define dividir 4
extern  int * dividir_1();
extern  int * dividir_1_svc();
extern int calculadora_1_freeresult ();
#endif /* K&R C */

/* the xdr functions */

#if defined(__STDC__) || defined(__cplusplus)
extern  bool_t xdr_datos (XDR *, datos*);

#else /* K&R C */
extern bool_t xdr_datos ();

#endif /* K&R C */

#ifdef __cplusplus
}
#endif

#endif /* !_CALCULADORA_H_RPCGEN */
