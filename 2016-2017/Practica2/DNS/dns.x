/* dns.x : Simulación de dns un sistema rpc */

const MAX_STRING = 30;			/* Tamaño máximo de un string */

typedef string str_t<MAX_STRING>; 	/* Definición del tipo string */

struct machine{				/* Estructura para pasar como parámetro a DNS */
	str_t name;
	str_t net;
	str_t ip;
};

program DNSPROG {
	version DNSVERS {
		str_t DNS1 (machine) = 1;
	} = 1;	
} = 0x3012225;

program DNSPROG2 {
	version DNSVERS {
		str_t DNS2 (machine) = 1;
	} = 1;	
} = 0x3012226;

