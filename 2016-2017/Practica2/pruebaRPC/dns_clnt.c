/*
 * Please do not edit this file.
 * It was generated using rpcgen.
 */

#include <memory.h> /* for memset */
#include "dns.h"

/* Default timeout can be changed using clnt_control() */
static struct timeval TIMEOUT = { 25, 0 };

str_t *
dns1_1(machine *argp, CLIENT *clnt)
{
	static str_t clnt_res;

	memset((char *)&clnt_res, 0, sizeof(clnt_res));
	if (clnt_call (clnt, DNS1,
		(xdrproc_t) xdr_machine, (caddr_t) argp,
		(xdrproc_t) xdr_str_t, (caddr_t) &clnt_res,
		TIMEOUT) != RPC_SUCCESS) {
		return (NULL);
	}
	return (&clnt_res);
}

str_t *
dns1rename_1(machine *argp, CLIENT *clnt)
{
	static str_t clnt_res;

	memset((char *)&clnt_res, 0, sizeof(clnt_res));
	if (clnt_call (clnt, DNS1rename,
		(xdrproc_t) xdr_machine, (caddr_t) argp,
		(xdrproc_t) xdr_str_t, (caddr_t) &clnt_res,
		TIMEOUT) != RPC_SUCCESS) {
		return (NULL);
	}
	return (&clnt_res);
}

str_t *
dns2_1(machine *argp, CLIENT *clnt)
{
	static str_t clnt_res;

	memset((char *)&clnt_res, 0, sizeof(clnt_res));
	if (clnt_call (clnt, DNS2,
		(xdrproc_t) xdr_machine, (caddr_t) argp,
		(xdrproc_t) xdr_str_t, (caddr_t) &clnt_res,
		TIMEOUT) != RPC_SUCCESS) {
		return (NULL);
	}
	return (&clnt_res);
}

str_t *
dns2rename_1(machine *argp, CLIENT *clnt)
{
	static str_t clnt_res;

	memset((char *)&clnt_res, 0, sizeof(clnt_res));
	if (clnt_call (clnt, DNS2rename,
		(xdrproc_t) xdr_machine, (caddr_t) argp,
		(xdrproc_t) xdr_str_t, (caddr_t) &clnt_res,
		TIMEOUT) != RPC_SUCCESS) {
		return (NULL);
	}
	return (&clnt_res);
}