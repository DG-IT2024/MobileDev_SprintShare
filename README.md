# 🚀 SprintShare: Project Management & Collaboration App

**Repository:** [MobileDev_SprintShare](https://github.com/DG-IT2024/MobileDev_SprintShare)  
**SprintShare Mockup – Figma:** 🔗 (https://www.figma.com/design/Z2kFAapaUgh4p9VFrBCprr/SprintShare-Mockup?node-id=0-1&p=f&t=OfjyP62zGI39K5qg-0)

---

### 📱 Project Context  
**SprintShare** is a mobile application prototype developed using **Android Studio**.  
The project focuses on **frontend design and user interface implementation** through XML layouts, Java-based Activities, and Material Design 3 components.  
It also demonstrates **navigation flow, interactivity, and responsive layout design** without backend integration.

---

### 🧑‍💻 Development Team
| Name | Program | Year Level |
|------|----------|-------------|
| Christian John Batuigas | BSIT | 3rd Year |
| Kurt Edraira | BSIT | 3rd Year |
| Danilo Giltendez | BSIT | 3rd Year |
| Kenneth Ian Lu | BSIT | 3rd Year |

---

## 🧠 Design Alternatives Considered

Before finalizing **SprintShare’s** current design and feature set, our team evaluated several alternatives. The table below summarizes these design decisions.

### 1. Navigation and Architecture

| Option | Description | Pros | Cons | Decision |
|--------|--------------|------|------|-----------|
| **Single-Activity (Fragment-based)** | All screens managed within one Activity using fragments. | Streamlined back-stack control. | Complex lifecycle management, prone to fragment overlap. | ❌ Rejected |
| **Multi-Activity Structure (Chosen)** | Separate Activities for Login, Account, ProjectHome, Tasks, etc. | Simple navigation, clear state separation, 1:1 match with Figma prototype screens. | Slightly more boilerplate for transitions. | ✅ Adopted |

### 2. Feature Scope (Frontend Only)

**UI-only prototype (Chosen)** | The app is implemented entirely at the frontend level, focusing on how users see and interact with the interface rather than on data storage or processing. It includes:<br><br>• **Static and semi-interactive screens** built with XML layouts.<br>• **Navigation flows** handled through `Intent` transitions and the `BottomNavigationView`.<br>• **Dialogs, forms, and recycler views** populated with placeholder or sample data to simulate realistic user interaction.<br>• **Animations and transitions** to represent app responsiveness and feedback without requiring backend computation.<br><br>This approach allows rapid testing of design decisions, layout responsiveness, and usability before connecting to any backend system. It demonstrates how the final app will look, feel, and behave from a user’s perspective. | ✅ Implemented to simulate real app behavior through interactive front-end design, layout logic, and placeholder data. |


### 3. UI Design System

| Option | Description | Decision |
|--------|--------------|----------|
| **Custom components** | Bespoke widgets per screen. | Rejected — inconsistent visuals, higher accessibility risk. |
| **Material Design 3 (Chosen)** | Adopted for responsive scaling, consistent typography, and accessibility compliance. | ✅ Ensures visual uniformity and device-independent layouts. |

---
## 🎨 Frontend Design & Usability Principles

### 1. Visual Hierarchy  
- Primary actions (e.g., “Add Task,” “Vote,” “Create Project”) use accent colors and elevation where applicable.  
- Secondary actions use neutral tones to maintain focus on key functions.  
- Ongoing UI adjustments are planned to improve button contrast and visual prominence.  

### 2. Contrast & Accessibility  
- The design follows the **WCAG 2.1 AA** principle for adequate text–background contrast (≥ 4.5 : 1).  
- Accessibility attributes (e.g., `contentDescription`) will be added to images and icons in the next iteration.  
- Buttons maintain **48 dp** tap targets for mobile usability.  

### 3. Consistency  
- Consistent spacing follows an **8 – 16 – 24 dp** rhythm across most layouts.  
- Unified typography targets:  
  - `H1` = 24 sp bold  
  - `Body` = 16 sp regular  
  - `Caption` = 12 sp italic  
- Shared colors and component shapes are defined in the app theme (`Theme.SprintShare`).  

### 4. Responsive Layout  
- Built primarily with **RelativeLayout** and **LinearLayout** to ensure structural clarity and compatibility.  
- Layouts use `match_parent` and `wrap_content` attributes for adaptive resizing.  
- Tested in **Android Studio emulators** for small, medium, and tablet devices.  

---

## 📈 Next Design Steps
- Expand **clickable prototype flow** between all major screens.  
- Add **visual feedback** (button states, dialog animations).  
- Conduct small-scale user testing to refine visual hierarchy and spacing.  
- Involves linking the interface to a backend (e.g., database, authentication, cloud APIs). 

