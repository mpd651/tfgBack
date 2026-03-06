exports = async function(changeEvent) {
  const audit = {
    operationType: changeEvent.operationType,
    timestamp: new Date(),
    namespace: changeEvent.ns,
    documentKey: changeEvent.documentKey,
    fullDocumentBefore: changeEvent.fullDocumentBeforeChange || null,
    fullDocumentAfter: changeEvent.fullDocument || null,
    updatedFields: changeEvent.updateDescription?.updatedFields || null,
    removedFields: changeEvent.updateDescription?.removedFields || null,
    clusterTime: changeEvent.clusterTime
  };

  const mongodb = context.services.get("Cluster0");
  const auditColl = mongodb.db("audit").collection("logs");

  await auditColl.insertOne(audit);
};