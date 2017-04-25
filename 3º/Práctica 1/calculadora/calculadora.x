struct datos {
	int dato1;
	int dato2;
};

program CALCULADORA {
	version CALCULADORA_VERSION { 	
		int sumar(datos) = 1; 	
		int restar(datos) = 2;
		int multiplicar(datos) = 3;
		int dividir(datos) = 4;	
	} = 1;	
} = 0x20000001;	
