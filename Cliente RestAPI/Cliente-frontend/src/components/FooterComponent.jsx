import React from 'react'

export const FooterComponent = () => {
    return (
        <footer className="bg-dark text-light text-center py-3 mt-4">
            <div className="container">
                <p className="mb-0">
          © {new Date().getFullYear()} Microservicios | Todos los derechos reservados
          </p>
          <p className="mt-2">
            <a href="#" className="text-light text-decoration-none">Privacidad</a>
            {' | '}
            <a href="#" className="text-light text-decoration-none">Términos</a>
            {' | '}
             <a href="#" className="text-light text-decoration-none">Contacto</a>
           </p><p>
           De la Cruz Martinez Maria Fernanda -21210854-
          </p>
            </div>
        </footer>
    )
}
