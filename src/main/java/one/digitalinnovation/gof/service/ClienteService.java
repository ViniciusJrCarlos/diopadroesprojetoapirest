package one.digitalinnovation.gof.service;

import one.digitalinnovation.gof.model.Cliente;

public interface ClienteService {

    //permite ter as mesmas implementacoes da mesma interface
    Iterable<Cliente> buscarTodos();
    Cliente buscarPorId(Long id);

    void inserir(Cliente cliente);

    void atualizar(Long id, Cliente cliente);

    void deletar(Long id);

}
