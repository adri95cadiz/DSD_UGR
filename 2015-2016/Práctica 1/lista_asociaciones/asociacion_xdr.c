/*
 * Please do not edit this file.
 * It was generated using rpcgen.
 */

#include "asociacion.h"

bool_t
xdr_ID (XDR *xdrs, ID *objp)
{
	register int32_t *buf;

	 if (!xdr_int (xdrs, objp))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_Key (XDR *xdrs, Key *objp)
{
	register int32_t *buf;

	 if (!xdr_string (xdrs, objp, TAMA))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_Content (XDR *xdrs, Content *objp)
{
	register int32_t *buf;

	 if (!xdr_string (xdrs, objp, TAMA))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_ElementoPtr (XDR *xdrs, ElementoPtr *objp)
{
	register int32_t *buf;

	 if (!xdr_pointer (xdrs, (char **)objp, sizeof (struct Elemento), (xdrproc_t) xdr_Elemento))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_ListaPtr (XDR *xdrs, ListaPtr *objp)
{
	register int32_t *buf;

	 if (!xdr_pointer (xdrs, (char **)objp, sizeof (struct Lista), (xdrproc_t) xdr_Lista))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_State (XDR *xdrs, State *objp)
{
	register int32_t *buf;

	 if (!xdr_enum (xdrs, (enum_t *) objp))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_Elemento (XDR *xdrs, Elemento *objp)
{
	register int32_t *buf;

	 if (!xdr_ID (xdrs, &objp->id))
		 return FALSE;
	 if (!xdr_Key (xdrs, &objp->key))
		 return FALSE;
	 if (!xdr_Content (xdrs, &objp->content))
		 return FALSE;
	 if (!xdr_ElementoPtr (xdrs, &objp->sig))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_Lista (XDR *xdrs, Lista *objp)
{
	register int32_t *buf;

	 if (!xdr_ID (xdrs, &objp->id))
		 return FALSE;
	 if (!xdr_Key (xdrs, &objp->key))
		 return FALSE;
	 if (!xdr_ListaPtr (xdrs, &objp->sig))
		 return FALSE;
	 if (!xdr_ElementoPtr (xdrs, &objp->first))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_ResultElemento (XDR *xdrs, ResultElemento *objp)
{
	register int32_t *buf;

	 if (!xdr_State (xdrs, &objp->state))
		 return FALSE;
	switch (objp->state) {
	case VALIDO:
		 if (!xdr_Content (xdrs, &objp->ResultElemento_u.content))
			 return FALSE;
		break;
	default:
		break;
	}
	return TRUE;
}

bool_t
xdr_ResultLista (XDR *xdrs, ResultLista *objp)
{
	register int32_t *buf;

	 if (!xdr_State (xdrs, &objp->state))
		 return FALSE;
	switch (objp->state) {
	case VALIDO:
		 if (!xdr_ElementoPtr (xdrs, &objp->ResultLista_u.elemPtr))
			 return FALSE;
		break;
	default:
		break;
	}
	return TRUE;
}

bool_t
xdr_datos_nueva_asociacion (XDR *xdrs, datos_nueva_asociacion *objp)
{
	register int32_t *buf;

	 if (!xdr_ID (xdrs, &objp->id))
		 return FALSE;
	 if (!xdr_Key (xdrs, &objp->key))
		 return FALSE;
	 if (!xdr_Content (xdrs, &objp->content))
		 return FALSE;
	return TRUE;
}

bool_t
xdr_datos_asociacion (XDR *xdrs, datos_asociacion *objp)
{
	register int32_t *buf;

	 if (!xdr_ID (xdrs, &objp->id))
		 return FALSE;
	 if (!xdr_Key (xdrs, &objp->key))
		 return FALSE;
	return TRUE;
}
