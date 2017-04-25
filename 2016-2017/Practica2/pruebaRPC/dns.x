/* dns.x : Simulación de dns en un sistema rpc */

const MAX_STRING = 30;			/* Tamaño máximo de un string */

typedef string str_t<MAX_STRING>; 	/* Definición del tipo string */

struct machine{				/* Estructura para pasar como parámetro a DNS */
	str_t name;
	str_t net;
	str_t ip;
};

program DNSPROG {			/* Primer programa que se encarga del DNS de la red 2 */
	version DNSVERS {
		str_t DNS1 (machine) = 1;	/* Llamada que busca un dispositivo en la red 2 y devuelve su ip */
		str_t DNS1rename (machine) = 2; /* Llamada que busca un dispositivo en la red 2 y renombra su ip */
	} = 1;	
} = 0x3012225;

program DNSPROG2 {			/* Segundo programa que se encarga del DNS de la red 1 */
	version DNSVERS {
		str_t DNS2 (machine) = 1;	/* Llamada que busca un dispositivo en la red 2 y devuelve su ip */
		str_t DNS2rename (machine) = 2;	/* Llamada que busca un dispositivo en la red 2 y renombra su ip */	
	} = 1;	
} = 0x3012226;

