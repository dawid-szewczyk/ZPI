package com.pwr.zpi.core.memory.semantic;

import com.pwr.zpi.core.memory.episodic.Observation;
import com.pwr.zpi.core.memory.semantic.identifiers.Identifier;

import java.util.*;

/**
 * Collection of individual models. Used to find or add new model.
 *
 * @author Mateusz Gawlowski
 * @author Weronika Wolska
 * @author Grzegorz Kostkowski
 */
public class IMCollection {

    private Set<IndividualModel> individualModelSet;

    /**
     * Map that stores names understood by humans and which identifier they represent
     */
    private Map<String, Identifier> lexicon;

    public IMCollection() {
        individualModelSet = new HashSet<>();
        lexicon = new HashMap<>();
    }

    public IMCollection(Set<IndividualModel> individualModelSet) {
        this.individualModelSet = individualModelSet;
    }

    /**
     * Checks whether object's observation has its representation amongst individual models.
     *
     * @param observation Given observation of some object.
     * @return Model found - true/false.
     */
    public boolean isObservationRepresented(Observation observation) {
        for(IndividualModel model : individualModelSet) {
            if (model.getIdentifier().equals(observation.getIdentifier()))
                return true;
        }
        return false;
    }

    /**
     * Simply adds new individual model to collection.
     *
     * @param individualModel New model.
     * @return True/false (success of add operation).
     */
    public boolean add(IndividualModel individualModel) {
        return individualModelSet.add(individualModel);
    }

    /**
     * Returns the individual model representing object of given identifier.
     *
     * @param identifier Identifier of object we are looking for..
     * @return Individual model of object.
     */
    public IndividualModel getRepresentationByIdentifier(Identifier identifier) {
        for(IndividualModel model : individualModelSet) {
            if (model.getIdentifier().equals(identifier))
                return model;
        }
        return null;
    }

    /**
     * Method finds a specific individual model based on given name.
     * @param name Common name for the object.
     * @return IndividualModel of that name.
     */
    public IndividualModel getRepresentationByName(String name)
    {
        name = name.toLowerCase();
        Identifier identifier = lexicon.get(name);
        return (identifier==null)? null: getRepresentationByIdentifier(identifier);
    }

    /**
     * Adds name of model that is understood by both agent and user to lexicon.
     *
     * @param identifier Identifier of individual model.
     * @param name New name of model.
     */
    public void addNameToModel(Identifier identifier, String name)
    {
        lexicon.put(name.toLowerCase(), identifier);
        if (getRepresentationByIdentifier(identifier) == null)
        individualModelSet.add(new IndividualModel(identifier, identifier.getType()));
    }

    /**
     * Method analyses incoming new observation and adds instance of IndividualModel into system if it is not present.
     * For convenience, method returns found or newly created IM.
     *
     * @param newObservation Observation which model is being checked
     */
    public IndividualModel captureNewIM(Observation newObservation) {
        Identifier newId = newObservation.getIdentifier();
        IndividualModel res;
        if ((res=getRepresentationByIdentifier(newId)) == null)
            individualModelSet.add(res=new IndividualModel(newId, newObservation.getType()));
        return res;
    }

    /**
     * Method checks if there is unregistered IM in given IM set and adds such IM.
     *
     * @param allIMs Set of all IMs.
     */
    public <T> void captureNewIM(Set<IndividualModel> allIMs) {
        for (IndividualModel im : allIMs)
            if (!new ArrayList(individualModelSet).contains(im))
                individualModelSet.add(im);
    }
}
