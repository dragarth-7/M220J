package mflix.api.daos.utils;

import mflix.api.models.Session;
import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.*;

public class SessionCodec implements CollectibleCodec<Session> {

    private final Codec<Document> documentCodec;

    public SessionCodec() {
        super();
        this.documentCodec = new DocumentCodec();
    }

    public void encode(
            BsonWriter bsonWriter, Session session, EncoderContext encoderContext) {
        Document sessionDoc = new Document();
        String userId = session.getUserId();
        String jwt = session.getJwt();

        if (userId != null) {
            sessionDoc.put("user_id", userId);
        }
        if (jwt != null) {
            sessionDoc.put("jwt", jwt);
        }

        documentCodec.encode(bsonWriter, sessionDoc, encoderContext);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Session decode(BsonReader bsonReader, DecoderContext decoderContext) {
        Document sessionDoc = documentCodec.decode(bsonReader, decoderContext);
        Session actor = new Session();
        actor.setId(sessionDoc.getObjectId("_id").toHexString());
        actor.setUserId(sessionDoc.getString("user_id"));
        actor.setJwt(sessionDoc.getString("jwt"));
        return actor;
    }

    @Override
    public Class<Session> getEncoderClass() {
        return Session.class;
    }

    @Override
    public Session generateIdIfAbsentFromDocument(Session session) {
        return !documentHasId(session) ? session.withNewId() : session;
    }

    @Override
    public boolean documentHasId(Session session) {
        return null != session.getId();
    }

    @Override
    public BsonString getDocumentId(Session session) {
        if (!documentHasId(session)) {
            throw new IllegalStateException("This document does not have an " + "_id");
        }

        return new BsonString(session.getUserId());
    }



}
