package zadanie2;

import io.jenetics.Chromosome;
import io.jenetics.Gene;
import io.jenetics.Mutator;
import io.jenetics.MutatorResult;
import io.jenetics.util.MSeq;

import java.util.random.RandomGenerator;

public class InverseOperator<G extends Gene<?, G>, C extends Comparable<? super C>> extends Mutator<G, C> {
    public InverseOperator(double probability) {
        super(probability);
    }

    public InverseOperator() {
        this(0.2);
    }

    protected MutatorResult<Chromosome<G>> mutate(Chromosome<G> chromosome, double p, RandomGenerator random) {
        MutatorResult result;
        if (chromosome.length() > 1 && random.nextDouble() < p) {
            MSeq<G> genes = MSeq.of(chromosome);
            int start = random.nextInt(0, genes.length());
            int stop = random.nextInt(0, genes.length());
            start = Math.min(start, stop);
            stop = Math.max(start, stop);

            var geneRange = genes.subSeq(start, stop).reverse();

            for (int i = start; i < stop; i++) {
                genes.set(i, geneRange.get(i - start));
            }

            result = new MutatorResult(chromosome.newInstance(genes.toISeq()), geneRange.length());
        } else {
            result = new MutatorResult(chromosome, 0);
        }

        return result;
    }
}
