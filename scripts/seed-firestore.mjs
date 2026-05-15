import { readFile } from "node:fs/promises";
import path from "node:path";
import { fileURLToPath } from "node:url";
import admin from "firebase-admin";

const __dirname = path.dirname(fileURLToPath(import.meta.url));
const rootDir = path.resolve(__dirname, "..");
const dataPath = path.join(rootDir, "firebase", "sample-data.json");

const idFields = {
  toys: "toyId",
  artisans: "artisanId",
  workshops: "workshopId",
  educational_content: "contentId"
};

function initializeFirebase() {
  if (!process.env.GOOGLE_APPLICATION_CREDENTIALS) {
    throw new Error(
      "GOOGLE_APPLICATION_CREDENTIALS is not set. Point it to your Firebase service account JSON file."
    );
  }

  admin.initializeApp({
    credential: admin.credential.applicationDefault()
  });
}

async function seedCollection(db, collectionName, records) {
  const idField = idFields[collectionName];
  if (!idField) {
    throw new Error(`No document id field configured for collection ${collectionName}`);
  }

  const batch = db.batch();
  for (const record of records) {
    const documentId = record[idField];
    if (!documentId) {
      throw new Error(`Missing ${idField} in ${collectionName} record`);
    }

    batch.set(db.collection(collectionName).doc(documentId), record, { merge: true });
  }

  await batch.commit();
  console.log(`Seeded ${records.length} ${collectionName} documents`);
}

async function main() {
  initializeFirebase();
  const db = admin.firestore();
  const sampleData = JSON.parse(await readFile(dataPath, "utf8"));

  for (const [collectionName, records] of Object.entries(sampleData)) {
    await seedCollection(db, collectionName, records);
  }

  console.log("Firestore sample data seeding complete.");
}

main().catch((error) => {
  console.error(error.message);
  process.exitCode = 1;
});
