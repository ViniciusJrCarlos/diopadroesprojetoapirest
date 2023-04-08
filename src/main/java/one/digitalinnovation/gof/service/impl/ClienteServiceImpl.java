package one.digitalinnovation.gof.service.impl;



import one.digitalinnovation.gof.model.Cliente;
import one.digitalinnovation.gof.model.ClienteRepository;
import one.digitalinnovation.gof.model.Endereco;
import one.digitalinnovation.gof.model.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Optional;

import one.digitalinnovation.gof.service.ClienteService;
import one.digitalinnovation.gof.service.ViaCepService;


@Service
public class ClienteServiceImpl implements  ClienteService {


    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;

    //@Bean
    @Autowired
    private ViaCepService viaCepService;

    //strategy implementa os metodos definidos na interface
    //facede: abstrair integracao com subsistemas, provendo uma interface simples
    //singleton injetar os componentes do spring com @autowired

    @Override
    public Iterable<Cliente> buscarTodos() {
        //buscar todos os clientes
        return clienteRepository.findAll();
    }

    @Override
    public Cliente buscarPorId(Long id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);
        return cliente.get();
    }

    @Override
    public void inserir(Cliente cliente) {

        salvarClienteComCep(cliente);
    }
    @Override
    public void atualizar(Long id, Cliente cliente) {

        //busca clientes por id, caso exista:

        Optional<Cliente> clienteBd = clienteRepository.findById(id);

        if(clienteBd.isPresent()) {

            salvarClienteCompCep(cliente);

        }

    }
    @Override
    public void deletar(Long id) {

        //delegar cliente por ID
        clienteRepository.deleteById(id);

    }
    //@Autowired
    private void salvarClienteComCep(Cliente cliente) {

    }

    //@Autowired
    private void salvarClienteCompCep(Cliente cliente) {

        //verifica se o endereco do cliente ja existe pelo cep
        String cep = cliente.getEndereco().getCep();
        Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
            //caso nao exista, integrar com viacep e persistir o retorno

            Endereco novoEndereco = viaCepService.consultarCep(cep);
            enderecoRepository.save(novoEndereco);
            return novoEndereco;


        });
        cliente.setEndereco(endereco);
        //inserir cliente vinculo ao novo entendeco ou existe
        clienteRepository.save(cliente);
    }

}
