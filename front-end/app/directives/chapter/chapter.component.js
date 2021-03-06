'use strict'

{
const ICON_SVG_CONTENTS = {
    h3: '<path d="M20,14.3400002 L21.4736328,14.34375 C22.5810602,14.34375 23.1347656,13.8017632 23.1347656,12.7177734 C23.1347656,12.2958963 23.002931,11.9516615 22.7392578,11.6850586 C22.4755846,11.4184557 22.103518,11.2851562 21.6230469,11.2851562 C21.2304668,11.2851562 20.8891616,11.3994129 20.5991211,11.6279297 C20.3090806,11.8564465 20.1640625,12.1406233 20.1640625,12.4804688 L17.2021484,12.4804688 C17.2021484,11.8066373 17.3896466,11.2060573 17.7646484,10.6787109 C18.1396503,10.1513646 18.6596646,9.73974757 19.324707,9.44384766 C19.9897494,9.14794774 20.7206991,9 21.5175781,9 C22.9414134,9 24.0605428,9.32519206 24.875,9.97558594 C25.6894572,10.6259798 26.0966797,11.5195256 26.0966797,12.65625 C26.0966797,13.207034 25.9282243,13.7270483 25.5913086,14.2163086 C25.2543928,14.7055689 24.7636751,15.1025375 24.1191406,15.4072266 C24.7988315,15.6533215 25.3320293,16.026853 25.71875,16.527832 C26.1054707,17.0288111 26.2988281,17.6484338 26.2988281,18.3867188 C26.2988281,19.5293026 25.8593794,20.4433559 24.9804688,21.1289062 C24.1015581,21.8144566 22.9472728,22.1572266 21.5175781,22.1572266 C20.6796833,22.1572266 19.9018591,21.9975602 19.184082,21.6782227 C18.466305,21.3588851 17.9228534,20.9165067 17.5537109,20.3510742 C17.1845685,19.7856417 17,19.1425817 17,18.421875 L19.9794922,18.421875 C19.9794922,18.8144551 20.1376937,19.1542954 20.4541016,19.4414062 C20.7705094,19.7285171 21.1601539,19.8720703 21.6230469,19.8720703 C22.1445339,19.8720703 22.5605453,19.7270522 22.8710938,19.4370117 C23.1816422,19.1469712 23.3369141,18.7763694 23.3369141,18.3251953 C23.3369141,17.6806608 23.1757829,17.2236342 22.8535156,16.9541016 C22.5312484,16.684569 22.0859403,16.5498047 21.5175781,16.5498047 L20,16.5498047 L20,14.3400002 Z M16,22 L13,22 L13,17 L9,17 L9,22 L6,22 L6,9 L9,9 L9,14 L13,14 L13,9 L16,9 L16,22 Z"></path>',
    p: '<path d="M20.9816895,17.2968826 C20.9816895,18.7734525 20.6491732,19.9526399 19.9841309,20.8344803 C19.3190885,21.7163206 18.2460994,22.1715698 17.1152344,22.1715698 C16.2421831,22.1715698 15.5625028,21.8489164 15,21.2102413 L15,25 L12,25 L12,11.9957809 L14.7685547,11.9957809 L14.8599997,12.8699999 C15.4283619,12.1668714 16.1718704,11.8199997 17.0976562,11.8199997 C18.2695371,11.8199997 19.3557096,12.2377886 20.0061035,13.1020508 C20.6564974,13.9663129 20.9816895,15.1542893 20.9816895,16.6660156 L20.9816895,17.2968826 Z M18.0197754,16.6220703 C18.0197754,14.9345619 17.352544,14.1051559 16.3681641,14.1051559 C15.6650355,14.1051559 15.2343762,14.3571066 15,14.8610153 L15,19.0953979 C15.2578138,19.6227443 15.6943325,19.8864136 16.3857422,19.8864136 C17.3291063,19.8864136 17.9904784,19.057633 18.0197754,17.4287186 L18.0197754,16.6220703 Z"></path>',
    anchor: '<path d="M15,12 L13,12 C10.790861,12 9,13.790861 9,16 C9,18.209139 10.790861,20 13,20 L15,20 L15,22 L17,22 L17,20 L19,20 C21.209139,20 23,18.209139 23,16 C23,13.790861 21.209139,12 19,12 L17,12 L17,10 L15,10 L15,12 Z M13,10 L19,10 C22.3137085,10 25,12.6862915 25,16 C25,19.3137085 22.3137085,22 19,22 L13,22 C9.6862915,22 7,19.3137085 7,16 C7,12.6862915 9.6862915,10 13,10 Z M12,15 L20,15 L20,17 L12,17 L12,15 Z"></path>',
    bold: '<path d="M19.6,15.79 C20.57,15.12 21.25,14.02 21.25,13 C21.25,10.74 19.5,9 17.25,9 L11,9 L11,23 L18.04,23 C20.13,23 21.75,21.3 21.75,19.21 C21.75,17.69 20.89,16.39 19.6,15.79 L19.6,15.79 Z M14,11.5 L17,11.5 C17.83,11.5 18.5,12.17 18.5,13 C18.5,13.83 17.83,14.5 17,14.5 L14,14.5 L14,11.5 Z M17.5,20.5 L14,20.5 L14,17.5 L17.5,17.5 C18.33,17.5 19,18.17 19,19 C19,19.83 18.33,20.5 17.5,20.5 Z"></path>',
    italic: '<polygon points="14 9 14 12 16.21 12 12.79 20 10 20 10 23 18 23 18 20 15.79 20 19.21 12 22 12 22 9"></polygon>',
    quote: '<path d="M10,21 L13,21 L15,17 L15,11 L9,11 L9,17 L12,17 L10,21 Z M18,21 L21,21 L23,17 L23,11 L17,11 L17,17 L20,17 L18,21 Z"></path>',
    unorderedlist: '<path d="M8,14.5 C7.17,14.5 6.5,15.17 6.5,16 C6.5,16.83 7.17,17.5 8,17.5 C8.83,17.5 9.5,16.83 9.5,16 C9.5,15.17 8.83,14.5 8,14.5 Z M8,8.5 C7.17,8.5 6.5,9.17 6.5,10 C6.5,10.83 7.17,11.5 8,11.5 C8.83,11.5 9.5,10.83 9.5,10 C9.5,9.17 8.83,8.5 8,8.5 Z M8,20.5 C7.17,20.5 6.5,21.18 6.5,22 C6.5,22.82 7.18,23.5 8,23.5 C8.82,23.5 9.5,22.82 9.5,22 C9.5,21.18 8.83,20.5 8,20.5 Z M11,23 L25,23 L25,21 L11,21 L11,23 Z M11,17 L25,17 L25,15 L11,15 L11,17 Z M11,9 L11,11 L25,11 L25,9 L11,9 Z"/>'
}
const EMBED_INSERTION_MARKER = '<div class="material-insertion-marker"></div>'
class controller extends Controller {

    $onChanges({ chapter }) {
        if (chapter && chapter.currentValue !== chapter.previousValue) {
            this.embedCache = this.embedCache || {}

            const isFirstChange = chapter.isFirstChange()
            this.$scope.chapter = chapter.currentValue

            if (!isFirstChange) this.updateEditors()

            if (this.isEditMode && !this.$scope.chapter.title) this.$scope.chapter.title = ''
            else if (this.$scope.chapter.title) this.$scope.chapterTitle = this.$scope.chapter.title
            else {
                this.$translate('PORTFOLIO_ENTER_CHAPTER_TITLE').then(missingTitle => this.$scope.chapterTitle = missingTitle)
            }

            // make sure there's always at least one empty block
            if (!Array.isArray(this.$scope.chapter.blocks))
                this.$scope.chapter.blocks = []

            if (!this.$scope.chapter.blocks.length)
                this.$scope.chapter.blocks.push({
                    narrow: false,
                    htmlContent: '<p><br></p>'
                })
        }
    }

    $onInit() {
        this.$scope.location = this.$location.absUrl()
        this.$scope.$watch('chapter.title', (title) =>
            this.$scope.slug = this.getSlug(`chapter-${this.index + 1}`)
        )
        this.currentLanguage = this.translationService.getLanguage()

        if (this.isEditMode) {
            this.$scope.isFocused = false
            this.$scope.isTitleFocused = false
            this.$scope.focusedBlockIdx = null

            this.$scope.$watch('chapter.blocks', this.onBlockChanges.bind(this), true)

            this.onClickOutside = this.onClickOutside.bind(this)
            document.addEventListener('mousedown', this.onClickOutside)
            document.addEventListener('touchstart', this.onClickOutside)

            this.$timeout(() =>
                Stickyfill.addOne(this.$element[0].querySelector('.sticky'))
            )

            this.unsubscribeInsertMaterials = this.$rootScope.$on('chapter:insertExistingMaterials', this.onInsertExistingMaterials.bind(this))

            // Need to patch it here cuz we need to access $translate
            MediumEditor.extensions.anchorPreview.prototype.getTemplate = () => {
                return `
                    <div class="medium-editor-toolbar-anchor-preview" id="medium-editor-toolbar-anchor-preview">
                        <div>${this.$translate.instant('EDIT_LINK')}: <a class="medium-editor-toolbar-anchor-preview-inner"></a></div>
                    </div>`
            }

            this.compileAndInjectEmbedToolbar()
        } else {
            this.$timeout(() => {
                for (let el of this.getEditorElements())
                    this.loadEmbeddedContents(el)

                // Remove blank paragraphs.
                for (let p of this.$element[0].querySelectorAll('p'))
                    if (!p.textContent.trim())
                        p.parentNode.removeChild(p)
            })
        }
    }

    $onDestroy() {
        if (this.isEditMode) {
            document.removeEventListener('click', this.onClickOutside)
            window.removeEventListener('resize', this.onResize)
            window.removeEventListener('scroll', this.onScroll)

            for (let el of this.getEditorElements()) {
                let editor = MediumEditor.getEditorFromElement(el)
                editor && editor.destroy()
            }

            this.unsubscribeInsertMaterials()

            if (this.isIOS()) {
                document.removeEventListener('touchmove', this.onIOSTouchMove)
                document.querySelector('.chapter-title-input').removeEventListener('focus', this.preventIOSPageShiftOnTitleInput)
            }

            if (this.embedToolbar)
                this.embedToolbar.parentNode.removeChild(this.embedToolbar)
        }
    }
    onClickOutside(evt) {
        if (this.$scope.isFocused) {
            let el = evt.target

            while (el !== null) {
                if (el === this.$element[0] ||
                    el.classList.contains('medium-editor-toolbar') ||
                    el.classList.contains('medium-editor-anchor-preview')
                )
                    return
                el = el.parentElement
            }

            this.$scope.isFocused = false
            this.$scope.isTitleFocused = false
            this.$scope.focusedBlockIdx = null
        }
    }
    onClickEmptyChapterArea(evt) {
        let el = evt.target

        while (el !== null) {
            if (el.classList.contains('chapter-block') ||
                el.classList.contains('chapter-title') ||
                el.classList.contains('chapter-btn') ||
                el.classList.contains('chapter-btn-large') ||
                el.classList.contains('chapter-pencil-button'))
                return
            el = el.parentElement
        }

        this.$scope.isTitleFocused
            ? this.focusTitle(true)
            : this.focusBlock(this.$scope.focusedBlockIdx, false, true)
    }
    onBlockChanges(blocks, previousBlocks) {
        // block elements are not in DOM before next loop
        if (!this.updatingState) {
            this.$timeout.cancel(this.blockChangeTimer)
            this.blockChangeTimer = this.$timeout(() => {
                for (let [idx, el] of this.getEditorElements().entries())
                    this.createEditor(blocks, idx, el)

                this.$timeout(() =>
                    this.updateEditors()
                )
            })
        }
    }
    createEditor(blocks, idx, el) {
        if (!MediumEditor.getEditorFromElement(el)) {
            const editor = new MediumEditor(el, {
                placeholder: idx === 0 && this.$scope.chapter.blocks.length === 1,
                toolbar: {
                    buttons: ['h3', 'anchor', 'bold', 'italic', 'quote', 'unorderedlist'].map(name => ({
                        name,
                        contentDefault: `<svg viewBox="0 0 32 32" preserveAspectRatio="xMidYMid meet">${ICON_SVG_CONTENTS[name]}</svg>`
                    }))
                },
                updateOnEmptySelection: true,
                paste: {
                    forcePlainText: false,
                    cleanPastedHTML: true,
                    /**
                     * These tags (and their contents) are removed during paste. All other tags that
                     * are not among these nor listed above in EDITOR_ALLOWED_TAGS are unwrapped.
                     */
                    cleanTags: EDITOR_FORBIDDEN_TAGS
                },
                anchor: {
                    linkValidation: true,
                    placeholderText: 'http://'
                },
                targetBlank: true
            })

            editor.subscribe('focus', this.onFocusBlock.bind(this, idx))
            editor.subscribe('blur', this.onBlurBlock.bind(this, idx))

            this.optimizePlaceholder(el, editor)
            this.watchEditableNodes(el)
        }
    }
    updateEditors() {
        /**
         * Update editor contents downstream:
         * $scope.chapter.blocks[idx].htmlContent -> editorElement.innerHTML
         */
        if (this.isEditMode) {
            for (let [idx, el] of this.getEditorElements().entries())
                if (el && this.$scope.chapter.blocks[idx]) {
                    const editor = MediumEditor.getEditorFromElement(el)
                    if (editor) {
                        el.innerHTML = this.$scope.chapter.blocks[idx].htmlContent

                        if (el.innerHTML && el.innerHTML !== '<p><br></p>')
                            el.classList.remove('medium-editor-placeholder')

                        this.loadEmbeddedContents(el)
                    }
                }
            this.registerSubchapters()
            this.discardEmptyElements()
        }
    }
    updateState(cb) {
        /**
         * Update input contents upstream:
         * editorElement.innerHTML -> $scope.chapter.blocks[idx].htmlContent
         */
        const sanitizeEditedHtml = (html) => {
            const wrapper = document.createElement('div')
            wrapper.innerHTML = html

            // 1) remove id atts from subchapters
            for (let subchapter of wrapper.querySelectorAll('.subchapter'))
                subchapter.removeAttribute('id')

            // 2) reduce embedded materials to <div> without any content
            for (let embed of wrapper.querySelectorAll('.chapter-embed-card'))
                this.unloadEmbeddedContent(embed)

            return wrapper.innerHTML
        }
        if (this.isEditMode) {
            this.updatingState = true
            for (let [idx, el] of this.getEditorElements().entries()) {
                const sanitizedHtml = sanitizeEditedHtml(el.innerHTML)

                if (this.$scope.chapter.blocks[idx] &&
                    this.$scope.chapter.blocks[idx].htmlContent !== sanitizedHtml
                )
                    this.$scope.chapter.blocks[idx].htmlContent = sanitizedHtml
            }
            this.$timeout(() => {
                this.updatingState = false

                if (typeof cb === 'function')
                    cb()
            })
        }
    }
    optimizePlaceholder(el, editor) {
        /**
         * Preventing placeholder flicker by delaying it's re-appearance
         */
        const removePlaceholder = (focused) => {
            this.$timeout.cancel(this.placeholderTimer)
            el.focused = focused
            el.classList.remove('medium-editor-placeholder')
        }
        editor.subscribe('focus', () => removePlaceholder(true))
        editor.subscribe('blur', () => {
            removePlaceholder(false)
            this.placeholderTimer = this.$timeout(() => {
                if (!el.focused && !el.innerHTML || el.innerHTML === '<p><br></p>')
                    el.classList.add('medium-editor-placeholder')
            }, 200)
        })
    }
    watchEditableNodes(el) {
        /**
         * 1) Unwrap html tags that are not allowed as soon as they are created.
         * 2) (Un)register subchapters upon creation/removal of <h3>
         * 3) Update state (and as a result sidemenu) when mutation originates from within H3
         * 4) Do not allow block-level elements to be nested in other block-level elements
         */
        const handleAddedNode = (node) => {
            if (node.parentNode && node.nodeType === Node.ELEMENT_NODE) {
                if (isEmbedElement(node))
                    return

                if (!EDITOR_ALLOWED_TAGS.includes(node.tagName))
                    return node.outerHTML = node.innerHTML

                unwrapBlockLevelParents(node)
                unwrapBlockLevelChildren(node, node)

                if (node.tagName === 'H3') {
                    node.classList.add('subchapter')
                    updateState()
                    this.registerSubchapters()
                }
            }
        }
        const handleRemovedNode = (node) => {
            if (node.nodeType === Node.ELEMENT_NODE && node.tagName === 'H3')
                updateState()
        }
        const handleSubtitleChange = (target) => {
            if (target.nodeType === Node.ELEMENT_NODE && target.tagName === 'H3' ||
                target.parentElement && target.parentElement.tagName === 'H3'
            )
                updateState()
        }
        // unwrap any nested block-level elements
        const unwrapBlockLevelParents = (child) => {
            const parent = child.parentElement
            if (isNodeBlockLevelElement(child) &&
                parent && !parent.classList.contains('chapter-block')
            ) {
                unwrapBlockLevelParents(parent) // recursion

                if (parent.parentElement &&
                    isNodeBlockLevelElement(parent) &&
                    !(parent.tagName === 'UL' && child.tagName === 'LI')
                ) {
                    console.log('%cunwrapBlockLevelParents', 'color:red', parent)
                    parent.outerHTML = parent.innerHTML
                }
            }
        }
        const unwrapBlockLevelChildren = (parent, root) => {
            if (isNodeBlockLevelElement(parent))
                for (let child of parent.children) {
                    unwrapBlockLevelChildren(child, root) // recursion

                    if (isNodeBlockLevelElement(child) &&
                        !(parent.tagName === 'UL' && child.tagName === 'LI' && root.tagName === 'UL')
                    ) {
                        console.log('%cunwrapBlockLevelChildren', 'color:red', child)
                        child.outerHTML = child.innerHTML
                    }
                }
        }
        const isNodeBlockLevelElement = (node) => {
            return node.nodeType === Node.ELEMENT_NODE
                && EDITOR_ALLOWED_BLOCK_LEVEL_TAGS.indexOf(node.tagName) > -1
                && !node.classList.contains('chapter-embed-card')
                && !node.classList.contains('chapter-embed-card__publishers-and-authors')
                && !node.classList.contains('chapter-embed-card__source')
                && !node.classList.contains('chapter-embed-card__thumbnail')
                && !node.classList.contains('chapter-embed-card__caption')
        }
        const updateState = () => {
            this.$timeout.cancel(this.subtitleChangeTimer)
            this.subtitleChangeTimer = this.$timeout(this.updateState.bind(this), 500)
        }
        const isEmbedElement = (el) => {
            while (el !== null) {
                if (el.classList.contains('chapter-embed-card') ||
                    el.classList.contains('embed-item') ||
                    el.classList.contains('embed-responsive-container') ||
                    el.classList.contains('embedded-material')
                )
                    return true
                if (el.classList.contains('chapter-block'))
                    return false
                el = el.parentElement
            }
        }

        if ('MutationObserver' in window) {
            el._mutationObserver = new MutationObserver(mutations =>
                mutations.forEach(({ addedNodes, removedNodes, target }) => {
                    /**
                     * addedNodes and removedNodes aren't exactly arrays and IE does not include
                     * forEach method on them.
                     */
                    [].forEach.call(addedNodes, handleAddedNode);
                    [].forEach.call(removedNodes, handleRemovedNode)
                    handleSubtitleChange(target)
                })
            )
            el._mutationObserver.observe(el, {
                subtree: true,
                childList: true,
                characterData: true
            })
        } else {
            el.addEventListener('DOMNodeInsertedIntoDocument', (evt) =>
                handleAddedNode(evt.target)
            )
            el.addEventListener('DOMNodeRemovedFromDocument', (evt) =>
                handleRemovedNode(evt.target)
            )
        }
    }
    registerSubchapters() {
        // add id attributes to all subchapters derived from subchapter titles
        for (let [subIdx, subEl] of this.$element[0].querySelectorAll('.subchapter').entries())
            subEl.id = this.getSlug(`subchapter-${this.index + 1}-${subIdx + 1}`)
    }
    loadEmbeddedContents(editorEl) {
        for (let embed of editorEl.querySelectorAll('.chapter-embed-card'))
            this.loadEmbeddedContent(embed)
    }
    loadEmbeddedContent(embed, update = false) {
        if (update)
            this.unloadEmbeddedContent(embed)

        embed.addEventListener('mouseenter', this.showEmbedToolbar.bind(this))
        embed.addEventListener('mouseleave', this.hideEmbedToolbar.bind(this))

        const { id } = embed.dataset
        if (!id
            || embed.classList.contains('chapter-embed-card--loading')
            || embed.classList.contains('chapter-embed-card--loaded')
        )
            return

        embed.classList.add('chapter-embed-card--loading')
        embed.setAttribute('contenteditable', 'false')
        /**
         * This is in place to prevent the context menu from appearing on touch devices when
         * user taps and holds on the embed element — to make way for the embed toolbar.
         */
        if (this.isTouchDevice())
            embed.addEventListener('contextmenu', (evt) => evt.preventDefault())

        const setContents = (embed, data) => {
            const fragment = document.createDocumentFragment()
            const onDoubleClick = embed.classList.contains('chapter-embed-card--media')
                ? this.openMediaDialog.bind(this, data)
                : undefined

            fragment.appendChild(compile({ data }, `<dop-embed data="data" hide-link="true"></dop-embedded-material>`))
            fragment.appendChild(compile({ data, onDoubleClick }, `<dop-embed-footer data="data" is-edit-mode="${this.isEditMode}" on-double-click="onDoubleClick()"></dop-embed-footer>`))

            embed.appendChild(fragment)

            if (data.deleted)
                embed.classList.add('is-deleted')
            embed.classList.add('chapter-embed-card--loaded')
            embed.classList.remove('chapter-embed-card--loading')
        }
        const compile = (scopeData, template) => {
            const $scope = this.$scope.$new(true)
            Object.assign($scope, scopeData)
            return this.$compile(template)($scope)[0]
        }
        const url = embed.classList.contains('chapter-embed-card--material')
            ? 'rest/material/chapter'
            : 'rest/media'

        // Read embeddable material/media data from cache or fetch it from the back-end
        this.embedCache[id]
            ? setContents(embed, this.embedCache[id])
            : this.serverCallService
                .makeGet(url, { id })
                .then(({ status, data }) => {
                    if (200 <= status && status < 300) {
                        this.embedCache[id] = data
                        setContents(embed, data)
                    } else {
                        embed.classList.add('chapter-embed-card--error')
                        embed.classList.remove('chapter-embed-card--loading')
                    }
                })
    }
    unloadEmbeddedContent(embed) {
        embed.classList.remove('chapter-embed-card--loading')
        embed.classList.remove('chapter-embed-card--loaded')
        embed.classList.remove('chapter-embed-card--error')
        embed.removeAttribute('contenteditable')

        while (embed.firstChild)
            embed.removeChild(embed.firstChild)
    }
    discardEmptyElements() {
        for (let el of this.$element[0].querySelectorAll('h3:empty, p:empty, li:empty, blockquote:empty'))
            el.parentNode.removeChild(el)

        for (let el of this.$element[0].querySelectorAll('ul:empty'))
            el.parentNode.removeChild(el)
    }
    getEditorElements() {
        return this.$element[0].querySelectorAll('.chapter-block') || []
    }
    getEditor(idx) {
        return MediumEditor.getEditorFromElement(this.getEditorElements()[idx])
    }
    getParentEditor(el) {
        while (el !== null) {
            if (el.classList.contains('chapter-block'))
                return el
            el = el.parentElement
        }
    }
    getChapterClassNames() {
        return Object.assign({
            'is-edit-mode': this.isEditMode
        }, this.isEditMode ? {
            'is-focused': this.$scope.isFocused,
            'is-title-focused': this.$scope.isTitleFocused,
            'is-block-focused': this.$scope.focusedBlockIdx !== null
        } : {
            'is-content-empty':
                !Array.isArray(this.$scope.chapter.blocks) ||
                !this.$scope.chapter.blocks.length ||
                !this.$scope.chapter.blocks.find(b => b.htmlContent)
        })
    }

    isSampleChapterTitle() {
        return (this.$scope.chapter.title === "")
    }
    getBlockClassNames(idx) {
        const { narrow } = this.$scope.chapter.blocks[idx]
        const classNames = {
            'is-narrow': narrow,
            'is-narrow-left': this.isNarrowLeft(idx),
            'is-focused': this.isEditMode && idx === this.$scope.focusedBlockIdx
        }
        return Object.assign(classNames, {
            'is-narrow-right': narrow && !classNames['is-narrow-left']
        })
    }
    getBlockPlaceholder(idx) {
        return idx === 0 && this.$scope.chapter.blocks.length === 1
            ? 'Alusta selle muutmisega (kliki siia) - lisa lõike, teksti, pilte, videosid, materjale e-koolikotist. Salvestamine toimub automaatselt. Jõudu tööle!'
            : ''
    }
    getToggleColumnWidthIcon() {
        const { narrow } = typeof this.$scope.focusedBlockIdx === 'number'
            ? this.$scope.chapter.blocks[this.$scope.focusedBlockIdx] || {}
            : {}

        return narrow
            ? '/images/chapter-toolbar-icon-column-wide.svg'
            : '/images/chapter-toolbar-icon-column-narrow.svg'
    }
    isNarrowLeft(idx) {
        let i = 0, colsFilled = 0

        for (let block of this.$scope.chapter.blocks) {
            if (this.$scope.chapter.blocks[idx].narrow &&
                i === idx &&
                !(colsFilled % 2)
            )
                return true

            colsFilled += block.narrow ? 1 : 2
            i ++
        }
    }
    focusTitle(clickOnContainer = false) {
        this.$timeout.cancel(this.blurTimer)
        this.$timeout(() => {
            this.$scope.isFocused = true
            this.$scope.isTitleFocused = true
            this.$scope.focusedBlockIdx = null
            this.$timeout(Stickyfill.refreshAll, 500)

            if (clickOnContainer)
                this.$element[0].querySelector('.chapter-title-input').focus()
        })
    }
    focusBlock(idx, restoreSelection = false, putCaretToEnd = false) {
        if (typeof idx !== 'number')
            idx = Math.max(0, this.$scope.chapter.blocks.length - 1)

        const editorEl = this.getEditorElements()[idx]
        const editor = editorEl && MediumEditor.getEditorFromElement(editorEl)

        if (editor) {
            editorEl.focus()

            if (restoreSelection && editor.selectionState) {
                editor.restoreSelection()
                delete editor.selectionState
            }

            if (putCaretToEnd) {
                // get the lowest element that contains everything
                let node = editorEl
                while (node.children.length === 1)
                    node = node.children[0]

                const range = document.createRange()
                range.selectNodeContents(node)
                const { length } = range.toString()

                editor.importSelection({ start: length, end: length })
                editor.getExtensionByName('toolbar').checkState()
            }
        }
    }
    onFocusBlock(idx) {
        this.$timeout.cancel(this.blurTimer)
        this.$timeout(() => {
            this.$scope.isFocused = true
            this.$scope.isTitleFocused = false
            this.$scope.focusedBlockIdx = idx
            this.$timeout(Stickyfill.refreshAll, 500)
        })
    }
    blurTitle() {
        this.$timeout.cancel(this.blurTimer)

        this.blurTimer = this.$timeout(() => {
            if (this.$scope.focusedBlockIdx === null)
                this.$scope.isFocused = false
        }, 500)
    }
    onBlurBlock(idx) {
        this.$timeout.cancel(this.blurTimer)

        /**
         * If focusedBlockIdx is still the same in next event loop then focus has left the blocks
         * and it should be set to null.
         */
        this.blurTimer = this.$timeout(() => {
            if (this.$scope.focusedBlockIdx === idx) {
                this.$scope.focusedBlockIdx = null

                if (!this.$scope.isTitleFocused)
                    this.$scope.isFocused = false
            }
        }, 500)
    }
    detachEditorListener(editor, eventName, fnName) {
        for (let listener of editor.events.customEvents[eventName])
            if (listener.name.includes(fnName)) {
                editor.unsubscribe(eventName, listener)
                return listener
            }
    }
    /**
     * Block actions
     */
    addBlock(htmlContent = '<p><br></p>') {
        this.$timeout.cancel(this.blurTimer)

        const { blocks } = this.$scope.chapter

        this.updateState(() => {
            blocks.push({
                htmlContent,
                narrow: this.isNarrowLeft(blocks.length - 1)
            })
            /**
             * 1st loop — block element has been injected to DOM
             * 2nd loop — Medium Editor is initialized on it
             * 3rd loop — Calling focusBlock in the 2nd loop does not invoke toolbar.checkState for
             * some reason and so editor toolbar does not appear.
             */
            this.$timeout(() =>
                this.$timeout(() =>
                    this.$timeout(() =>
                        this.focusBlock(blocks.length - 1)
                    )
                )
            )
        })
    }
    deleteBlock() {
        this.$timeout.cancel(this.blurTimer)

        const { focusedBlockIdx, chapter: { blocks } } = this.$scope
        const deleteBlock = () =>
            this.updateState(() => {
                blocks.splice(focusedBlockIdx, 1)
                this.focusBlock(Math.min(focusedBlockIdx, blocks.length - 1), false, true)
            })

        if (focusedBlockIdx !== null) {
            const { innerHTML } = this.getEditorElements()[focusedBlockIdx]

            /**
             * @todo Properly restore selection if delete confirmation is declined
             */
            innerHTML && innerHTML !== '<p><br></p>' && !this.isIOS()
                ? this.dialogService.showDeleteConfirmationDialog('ARE_YOU_SURE_DELETE', '', deleteBlock)
                : deleteBlock()
        }
    }
    beforeToggleBlockWidth() {
        this.$timeout.cancel(this.blurTimer)

        const { focusedBlockIdx } = this.$scope

        // save selection prior to mutation
        if (focusedBlockIdx !== null)
            this.getEditor(focusedBlockIdx).saveSelection()
    }
    toggleBlockWidth() {
        const { focusedBlockIdx, chapter: { blocks } } = this.$scope

        if (focusedBlockIdx !== null) {
            this.updateState()
            blocks[focusedBlockIdx].narrow = !blocks[focusedBlockIdx].narrow
            this.focusBlock(focusedBlockIdx, true)
        }
    }
    // Leaving this unused since it did not perform properly on WIN platform
    beforeMoveBlock(up = false) {
        this.$timeout.cancel(this.blurTimer)

        /**
         * Copy selection state from active block to the destination block because in reality we're
         * not moving blocks (editors) around but instead swapping content between them.
         */
        const { focusedBlockIdx } = this.$scope
        if (focusedBlockIdx !== null) {
            const newIdx = focusedBlockIdx + (up ? -1 : 1)
            const fromEditor = this.getEditor(focusedBlockIdx)
            const toEditor = this.getEditor(newIdx)
            const onFocus = this.detachEditorListener(toEditor, 'focus', 'onFocusBlock')

            toEditor.importSelection(fromEditor.exportSelection())
            toEditor.saveSelection()
            toEditor.subscribe('focus', onFocus)
        }
    }
    moveBlock(up = false) {
        const { focusedBlockIdx, chapter: { blocks } } = this.$scope

        if (focusedBlockIdx !== null) {
            const newIdx = focusedBlockIdx + (up ? -1 : 1)

            this.updateState(() => {
                blocks.splice(newIdx, 0, blocks.splice(focusedBlockIdx, 1)[0])
                this.focusBlock(newIdx, true)
            })
        }
    }
    beforeAddEmbed() {
        this.clearEmbedInsertionData()
        window.embedInsertionChapterIdx = this.index

        if (typeof this.$scope.focusedBlockIdx === 'number') {
            const editorEl = this.getEditorElements()[this.$scope.focusedBlockIdx]

            this.insertHtmlAfterSelection(EMBED_INSERTION_MARKER)
            window.embedInsertionBlockIdx = this.$scope.focusedBlockIdx
            window.embedInsertionBlockContents = editorEl.innerHTML

            const marker = editorEl.querySelector('.material-insertion-marker')
            marker.parentNode.removeChild(marker)
        }
    }
    onClickAddExistingMaterial(preferred = false) {
        /**
         * This calls this.updateChaptersStateFromEditors() and then initiates
         * a POST request to rest/portfolio/update.
         */
        this.onAddExistingMaterial()

        this.$rootScope.$broadcast(
            window.innerWidth >= BREAK_SM
                ? 'detailedSearch:open'
                : 'mobileSearch:open'
        )
        this.$timeout(() => {
            this.searchService.setIsFavorites(preferred)
            this.searchService.setIsRecommended(preferred)
            this.searchService.setType('material')
            document.getElementById('header-search-input').focus()
            this.$rootScope.$broadcast('detailedSearch:search')
        })
        // document.cookie = 'visitedAddMaterialPage=true;'
    }
    onInsertExistingMaterials(evt, chapterIdx, materials) {
        /**
         * These timeouts are necessary to ensure block elements are created in DOM and
         * Medium Editors are initialized on them.
         */
        if (chapterIdx === this.index)
            this.$timeout(() =>
                this.$timeout(() =>
                    this.insertEmbeds(
                        this.getMaterialsMarkup(materials),
                        materials[materials.length - 1].id,
                        true
                    )
                )
            )
    }
    onClickAddNewMaterial() {
        this.$mdDialog.show({
            templateUrl: 'addMaterialDialog.html',
            controller: 'addMaterialDialogController',
            controllerAs: '$ctrl',
            locals: {
                isEditMode: false,
                isAddToPortfolio: true
            }
        }).then(material => {
            this.embedCache[material.id] = material
            this.insertEmbeds(this.getMaterialsMarkup([material], material.id))
        })
    }
    getMaterialsMarkup(materials) {
        return materials.reduce((html, { id }) =>
            html + `<div class="chapter-embed-card chapter-embed-card--material" data-id="${id}"></div><p><br></p>`,
            ''
        )
    }
    openMediaDialog(editableMedia) {
        /**
         * @todo Restore caret position when cancelling the dialog
         */
        const options = {
            templateUrl: 'views/addMediaDialog/addMediaDialog.html',
            controller: 'addMediaDialogController',
            controllerAs: '$ctrl',
            locals: {
                isEditMode: !!editableMedia
            }
        }
        if (editableMedia) {
            options.scope = this.$scope.$new(true)
            options.scope.media = editableMedia
        }

        this.$mdDialog.show(options).then(media => {
            this.embedCache[media.id] = media
            const editableElement = this.$element[0].querySelector(`[data-id="${media.id}"]`)

            editableMedia && editableElement
                ? this.loadEmbeddedContent(editableElement, true)
                : this.insertEmbeds(
                    `<div class="chapter-embed-card chapter-embed-card--media" data-id="${media.id}"></div><p><br></p>`,
                    media.id
                )
        })
    }
    insertEmbeds(embedsHtml, lastId, isAddExisting = false) {
        const insertingAtMarker = typeof window.embedInsertionBlockIdx === 'number'
        const editorElements = this.getEditorElements()
        const editorEl = insertingAtMarker
            ? editorElements[window.embedInsertionBlockIdx]
            : editorElements[editorElements.length - 1]

        insertingAtMarker
            ? editorEl.innerHTML = window.embedInsertionBlockContents.replace(EMBED_INSERTION_MARKER, embedsHtml)
            : editorEl.innerHTML += embedsHtml

        this.updateState()
        this.loadEmbeddedContents(editorEl)
        this.clearEmbedInsertionData()

        const focusBlock = () => {
            insertingAtMarker
                ? this.focusBlock(window.embedInsertionBlockIdx)
                : this.focusBlock()

            const lastInsertedEmbed = editorEl.querySelector(`[data-id="${lastId}"]`)
            this.scrollToElement(lastInsertedEmbed)
            this.putCaretAfterNode(lastInsertedEmbed)
        }
        /**
         * Yet another timeout is necessary to wait for the editor to become properly focusable.
         * When existing materials are being added the portfolio edit view is reloaded as the user
         * arrives back from search view.
         */
        isAddExisting
            ? this.$timeout(focusBlock, 500)
            : focusBlock()
    }
    clearEmbedInsertionData() {
        delete window.embedInsertionChapterIdx
        delete window.embedInsertionBlockIdx
        delete window.embedInsertionBlockContents
    }
    insertHtmlAfterSelection(html) {
        // courtesy of https://stackoverflow.com/a/6691294
        let sel, range, node

        if (window.getSelection) {
            sel = window.getSelection()

            if (sel.getRangeAt && sel.rangeCount) {
                range = window.getSelection().getRangeAt(0)
                range.collapse(false)

                // Range.createContextualFragment() would be useful here but is
                // non-standard and not supported in all browsers (IE9, for one)
                const el = document.createElement('div')
                const frag = document.createDocumentFragment()

                el.innerHTML = html

                let node, lastNode
                while (node = el.firstChild)
                    lastNode = frag.appendChild(node)

                range.insertNode(frag)
            }
        } else if (document.selection && document.selection.createRange) {
            range = document.selection.createRange()
            range.collapse(false)
            range.pasteHTML(html)
        }
    }
    putCaretAfterNode(node) {
        const selection = window.getSelection()
        if (selection.rangeCount) {
            const range = window.getSelection().getRangeAt(0)
            range.setStartAfter(node)
            range.setEndAfter(node)
            selection.removeAllRanges()
            selection.addRange(range)
        }
    }
    /**
     * Embed toolbar (float left|right / full-width)
     */
    compileAndInjectEmbedToolbar() {
        this.$embedToolbarScope = this.$scope.$new(true)
        this.$embedToolbarScope.isVisible = false
        this.$embedToolbarScope.target = undefined

        const embedToolbarTemplate = `
            <dop-embed-toolbar
                is-visible="isVisible"
                target="target"
            ></dop-embed-toolbar>`
        this.embedToolbar = this.$compile(embedToolbarTemplate)(this.$embedToolbarScope)[0]

        document.body.appendChild(this.embedToolbar)
    }
    showEmbedToolbar(evt) {
        const parentEditor = this.getParentEditor(evt.target)
        if (this.$embedToolbarScope && parentEditor && !parentEditor.classList.contains('is-narrow')) {
            this.$embedToolbarScope.isVisible = true
            this.$embedToolbarScope.target = evt.target
            this.$embedToolbarScope.$digest()
        }
    }
    hideEmbedToolbar() {
        if (this.$embedToolbarScope) {
            this.$embedToolbarScope.isVisible = false
            this.$embedToolbarScope.$digest()
        }
    }
}
controller.$inject = [
    '$scope',
    '$rootScope',
    '$element',
    '$timeout',
    '$translate',
    '$mdDialog',
    '$compile',
    'dialogService',
    'iconService',
    'serverCallService',
    'translationService',
    'searchService',
    '$location'
]
component('dopChapter', {
    bindings: {
        index: '<',
        numChapters: '<',
        chapter: '<',
        onMoveUp: '&',
        onMoveDown: '&',
        onDelete: '&',
        onAddExistingMaterial: '&',
        isEditMode: '<'
    },
    templateUrl: 'directives/chapter/chapter.html',
    controller
})
}
